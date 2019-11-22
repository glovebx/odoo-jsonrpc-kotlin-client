package com.ria4.odoo.di.module

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import com.ria4.odoo.data.cache.MemoryCache
import com.ria4.odoo.data.mapper.Mapper
import com.ria4.odoo.data.network.AuthApiService
import com.ria4.odoo.data.network.CommonApiService
import com.ria4.odoo.data.network.UserApiService
import com.ria4.odoo.data.repository.CommonDataRepository
import com.ria4.odoo.data.repository.UserDataRepository
import com.ria4.odoo.di.scope.PerActivity
import com.ria4.odoo.domain.repository.CommonRepository
import com.ria4.odoo.domain.repository.UserRepository

/**
 * Created by glovebx on 11.11.2019.
 */
@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @PerActivity
    @Provides
    fun providesActivity(): AppCompatActivity = activity

    @PerActivity
    @Provides
    fun providesLayoutInflater(activity: AppCompatActivity): LayoutInflater =
            LayoutInflater.from(activity)

    @PerActivity
    @Provides
    fun providesFragmentManager(activity: AppCompatActivity): FragmentManager =
            activity.supportFragmentManager

    @PerActivity
    @Provides
    fun provideCommonRepository(commonApiService: CommonApiService,
                                gson: Gson,
                                memoryCache: MemoryCache,
                                mapper: Mapper): CommonRepository
            = CommonDataRepository(commonApiService, gson, memoryCache, mapper)

    @PerActivity
    @Provides
    fun provideUserRepository(authApiService: AuthApiService,
                              userApiService: UserApiService,
                              gson: Gson,
                              memoryCache: MemoryCache,
                              mapper: Mapper): UserRepository
            = UserDataRepository(authApiService, userApiService, gson, memoryCache, mapper)

}