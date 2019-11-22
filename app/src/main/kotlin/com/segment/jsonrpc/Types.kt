package com.segment.jsonrpc

/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.reflect.GenericArrayType
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType
import java.util.Arrays

/** Factory methods for types.  */
internal object Types {
    val EMPTY_TYPE_ARRAY = arrayOf<Type>()

    /**
     * Returns a new parameterized type, applying `typeArguments` to `rawType`.
     */
    fun newParameterizedType(rawType: Type, vararg typeArguments: Type): ParameterizedType {
        return ParameterizedTypeImpl(null, rawType, *typeArguments)
    }

    /**
     * Returns a type that is functionally equal but not necessarily equal according to [ ][Object.equals].
     */
    fun canonicalize(type: Type?): Type? {
        if (type is Class<*>) {
            val c = type as Class<*>?
            return if (c!!.isArray) GenericArrayTypeImpl(canonicalize(c.componentType)) else c
        } else if (type is ParameterizedType) {
            val p = type as ParameterizedType?
            return ParameterizedTypeImpl(p!!.ownerType,
                    p.rawType, *p.actualTypeArguments)
        } else if (type is GenericArrayType) {
            val g = type as GenericArrayType?
            return GenericArrayTypeImpl(g!!.genericComponentType)
        } else if (type is WildcardType) {
            val w = type as WildcardType?
            return WildcardTypeImpl(w!!.upperBounds, w.lowerBounds)
        } else {
            return type // This type is unsupported!
        }
    }

    fun equal(a: Any?, b: Any): Boolean {
        return a === b || a != null && a == b
    }

    /** Returns true if `a` and `b` are equal.  */
    fun equals(a: Type, b: Type): Boolean {
        if (a === b) {
            return true // Also handles (a == null && b == null).
        } else if (a is Class<*>) {
            return a == b // Class already specifies equals().
        } else if (a is ParameterizedType) {
            if (b !is ParameterizedType) return false
            val aTypeArguments = if (a is ParameterizedTypeImpl)
                a.typeArguments
            else
                a.actualTypeArguments
            val bTypeArguments = if (b is ParameterizedTypeImpl)
                b.typeArguments
            else
                b.actualTypeArguments
            return (equal(a.ownerType, b.ownerType)
                    && a.rawType == b.rawType
                    && Arrays.equals(aTypeArguments, bTypeArguments))
        } else return if (a is GenericArrayType) {
            if (b !is GenericArrayType) false else equals(a.genericComponentType, b.genericComponentType)
        } else if (a is WildcardType) {
            if (b !is WildcardType) false else Arrays.equals(a.upperBounds, b.upperBounds) && Arrays.equals(a.lowerBounds, b.lowerBounds)
        } else if (a is TypeVariable<*>) {
            if (b !is TypeVariable<*>) false else a.genericDeclaration === b.genericDeclaration && a.name == b.name
        } else {
            // This isn't a supported type. Could be a generic array type, wildcard type, etc.
            false
        }
    }

    fun hashCodeOrZero(o: Any?): Int {
        return o?.hashCode() ?: 0
    }

    fun typeToString(type: Type?): String {
        return if (type is Class<*>) type.name else type!!.toString()
    }

    fun checkNotPrimitive(type: Type) {
        require(!(type is Class<*> && type.isPrimitive))
    }

    private class ParameterizedTypeImpl internal constructor(ownerType: Type?, rawType: Type, vararg typeArguments: Type) : ParameterizedType {
        private val ownerType: Type?
        private val rawType: Type?
        internal val typeArguments: Array<Type>

        init {
            // require an owner type if the raw type needs it
            if (rawType is Class<*>) {
                val rawTypeAsClass = rawType
                val isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.modifiers) || rawTypeAsClass.enclosingClass == null
                require(!(ownerType == null && !isStaticOrTopLevelClass))
            }

            this.ownerType = if (ownerType == null) null else canonicalize(ownerType)
            this.rawType = canonicalize(rawType)

            val tas: Array<out Type> = typeArguments.clone()
            this.typeArguments = Array(tas.size) {
                tas[it]
            }
//            this.typeArguments = typeArguments.clone()
            for (t in this.typeArguments.indices) {
                if (this.typeArguments[t] == null) throw NullPointerException()
                checkNotPrimitive(this.typeArguments[t])
                this.typeArguments[t] = canonicalize(this.typeArguments[t])!!
            }
        }

        override fun getActualTypeArguments(): Array<out Type> {
            return typeArguments.clone()
        }

        override fun getRawType(): Type? {
            return rawType
        }

        override fun getOwnerType(): Type? {
            return ownerType
        }

        override fun equals(other: Any?): Boolean {
            return other is ParameterizedType && Types.equals(this, other as ParameterizedType)
        }

        override fun hashCode(): Int {
            return (Arrays.hashCode(typeArguments)
                    xor rawType!!.hashCode()
                    xor hashCodeOrZero(ownerType))
        }

        override fun toString(): String {
            val result = StringBuilder(30 * (typeArguments.size + 1))
            result.append(typeToString(rawType))

            if (typeArguments.isEmpty()) {
                return result.toString()
            }

            result.append("<").append(typeToString(typeArguments[0]))
            for (i in 1 until typeArguments.size) {
                result.append(", ").append(typeToString(typeArguments[i]))
            }
            return result.append(">").toString()
        }
    }

    private class GenericArrayTypeImpl(componentType: Type?) : GenericArrayType {
        private val componentType: Type?

        init {
            this.componentType = canonicalize(componentType)
        }

        override fun getGenericComponentType(): Type? {
            return componentType
        }

        override fun equals(o: Any?): Boolean {
            return o is GenericArrayType && Types.equals(this, o as GenericArrayType)
        }

        override fun hashCode(): Int {
            return componentType!!.hashCode()
        }

        override fun toString(): String {
            return typeToString(componentType) + "[]"
        }
    }

    /**
     * The WildcardType interface supports multiple upper bounds and multiple lower bounds. We only
     * support what the Java 6 language needs - at most one bound. If a lower bound is set, the upper
     * bound must be Object.class.
     */
    private class WildcardTypeImpl(upperBounds: Array<Type>, lowerBounds: Array<Type>) : WildcardType {
        private val upperBound: Type?
        private val lowerBound: Type?

        init {
            require(lowerBounds.size <= 1)
            require(upperBounds.size == 1)

            if (lowerBounds.size == 1) {
                if (lowerBounds[0] == null) throw NullPointerException()
                checkNotPrimitive(lowerBounds[0])
                require(!(upperBounds[0] !== Any::class.java))
                this.lowerBound = canonicalize(lowerBounds[0])
                this.upperBound = Any::class.java
            } else {
                if (upperBounds[0] == null) throw NullPointerException()
                checkNotPrimitive(upperBounds[0])
                this.lowerBound = null
                this.upperBound = canonicalize(upperBounds[0])
            }
        }

        override fun getUpperBounds(): Array<Type> {
            return arrayOf<Type>(upperBound!!)
        }

        override fun getLowerBounds(): Array<Type> {
            return if (lowerBound != null) arrayOf(lowerBound) else EMPTY_TYPE_ARRAY
        }

        override fun equals(other: Any?): Boolean {
            return other is WildcardType && Types.equals(this, other as Type)
        }

        override fun hashCode(): Int {
            // This equals Arrays.hashCode(getLowerBounds()) ^ Arrays.hashCode(getUpperBounds()).
            return (if (lowerBound != null) 31 + lowerBound.hashCode() else 1) xor 31 + upperBound!!.hashCode()
        }

        override fun toString(): String {
            return if (lowerBound != null) {
                "? super " + typeToString(lowerBound)
            } else if (upperBound === Any::class.java) {
                "?"
            } else {
                "? extends " + typeToString(upperBound)
            }
        }
    }
}
