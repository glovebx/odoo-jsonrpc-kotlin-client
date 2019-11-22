package com.ria4.odoo.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.bind.ModifiedObjectTypeAdapter
import com.google.gson.internal.bind.ObjectTypeAdapter
import com.ria4.odoo.data.mapper.Mapper
import dagger.Module
import dagger.Provides
import com.ria4.odoo.data.db.AppDatabase
import com.ria4.odoo.data.db.UserDatabaseRepository
import com.ria4.odoo.domain.database.repository.UserDaoRepository
import com.ria4.odoo.domain.entity.User
import io.reactivex.Single
import java.util.*
import javax.inject.Singleton

/**
 * Created by glovebx on 11.11.2019.
 */
@Module
class ApplicationModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase =
            Room.databaseBuilder(application, AppDatabase::class.java, "odoo").build()

    @Singleton
    @Provides
    fun provideMapper(): Mapper = Mapper()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().registerTypeAdapterFactory(
            UnknownPropertiesTypeAdapterFactory.instance)
            .create().apply {
                // 解决gson把int类型的数据cast为double的问题
                val factories = this.javaClass.getDeclaredField("factories")
                factories.isAccessible = true
                val o = factories.get(this)
                val declaredClasses = Collections::class.java.declaredClasses
                for (c in declaredClasses) {
                    if ("java.util.Collections\$UnmodifiableList" == c.name) {
                        val listField = c.getDeclaredField("list")
                        listField.isAccessible = true
                        val list = listField.get(o) as MutableList<TypeAdapterFactory>
                        val index = list.indexOf(ObjectTypeAdapter.FACTORY)
                        // 替换
                        if (index >=0 ) {
                            list[index] = ModifiedObjectTypeAdapter.FACTORY
                        }
                        break
                    }
                }
            }

    @Singleton
    @Provides
    fun provideUserDaoRepository(appDatabase: AppDatabase): UserDaoRepository = UserDatabaseRepository(appDatabase.userDao())

}