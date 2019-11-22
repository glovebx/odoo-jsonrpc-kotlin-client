package com.ria4.odoo.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import com.ria4.odoo.BuildConfig
import com.ria4.odoo.data.network.*
import com.ria4.odoo.data.pref.Preferences
import com.ria4.odoo.presentation.utils.CompositeDisposableWrapper
import com.segment.jsonrpc.JsonRPCConverterFactory
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by glovebx on 11.11.2019.
 */
@Module
class ApiModule {

    @Singleton
    @Provides
    @Named("odooEndpoint")
    fun odooEndpoint() = ApiConstants.ODOO_ENDPOINT

    @Singleton
    @Provides
    fun interceptor(preferences: Preferences) = JsonHeaderInterceptor(preferences)

    @Singleton
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            okHttpBuilder.addInterceptor(logging)
        }
        return okHttpBuilder.apply {
            readTimeout(15.toLong(), TimeUnit.SECONDS)
            connectTimeout(15.toLong(), TimeUnit.SECONDS)
        }
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(JsonRPCConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Singleton
    @Provides
    @Named("odooRetrofit")
    fun provideOdooRetrofit(retrofitBuilder: Retrofit.Builder,
                            okHttpClientBuilder: OkHttpClient.Builder,
                            interceptor: JsonHeaderInterceptor,
                            @Named("odooEndpoint") baseUrl: String): Retrofit {
        val client = okHttpClientBuilder.addInterceptor(interceptor).build()
        return retrofitBuilder
                .client(client)
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    fun provideCommonApiService(@Named("odooRetrofit") retrofit: Retrofit): CommonApiService {
        return retrofit.create(CommonApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApiService(@Named("odooRetrofit") retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserApiService(@Named("odooRetrofit") retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCompositeDisposable() = CompositeDisposableWrapper()
}