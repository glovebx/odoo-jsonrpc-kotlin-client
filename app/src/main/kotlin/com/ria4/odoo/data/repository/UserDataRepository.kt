package com.ria4.odoo.data.repository

import com.google.gson.Gson
import com.ria4.odoo.data.cache.MemoryCache
import com.ria4.odoo.data.mapper.Mapper
import com.ria4.odoo.data.network.AuthApiService
import com.ria4.odoo.data.network.UserApiService
import com.ria4.odoo.data.pref.Preferences
import com.ria4.odoo.data.request.CommonRPCRequest
import com.ria4.odoo.domain.entity.User
import com.ria4.odoo.domain.repository.UserRepository
import io.reactivex.*

/**
 * Created by glovebx on 11.11.2019.
 */
class UserDataRepository(
        private val authApiService: AuthApiService,
        private val userApiService: UserApiService,
        private var gson: Gson,
        private val memoryCache: MemoryCache,
        private val mapper: Mapper) : UserRepository {

    override fun authenticate(request: CommonRPCRequest): Flowable<User> = authApiService.authenticate(request).map {
        val db = request.args[0] as String
        val userName = request.args[1] as String
        val password = request.args[2] as String
        User(null, db, it, password, userName)
    }

    override fun saveToken(token: String?) {
    }

    override fun clearLoginData() {
    }

    override fun isUserLoggedIn() = false

    override fun clearCache() {
        memoryCache.clearCache()
    }
}