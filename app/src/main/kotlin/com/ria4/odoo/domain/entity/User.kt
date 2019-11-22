package com.ria4.odoo.domain.entity

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ria4.odoo.presentation.utils.extensions.emptyString
import kotlinx.android.parcel.Parcelize

/**
 * Created by glovebx on 11.11.2019.
 */
@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = "user", indices = [Index(value = ["db", "uid"], unique = true)])
data class User constructor(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long?,

        @ColumnInfo(name = "db")
        val db: String,

        @ColumnInfo(name = "uid")
        val uid: Int,

        @ColumnInfo(name = "password")
        val password: String,

        @ColumnInfo(name = "user_name")
        val userName: String,

        @ColumnInfo(name = "user_icon")
        val userIcon: String? = emptyString,

        @ColumnInfo(name = "current")
        val current: Boolean = false

) : Parcelable