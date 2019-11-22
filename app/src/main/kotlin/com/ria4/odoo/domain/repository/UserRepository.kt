package com.ria4.odoo.domain.repository

import com.ria4.odoo.data.request.CommonRPCRequest
import com.ria4.odoo.data.request.DbRPCRequest
import com.ria4.odoo.domain.entity.ServerVersion
import com.ria4.odoo.domain.entity.User
import io.reactivex.Flowable

/**
 * Created by glovebx on 11.11.2019.
 */
interface UserRepository {

    fun authenticate(request: CommonRPCRequest): Flowable<User>

    fun isUserLoggedIn(): Boolean

//    fun getUser(cookie: String): Flowable<User>

    fun clearCache()

    fun clearLoginData()

    fun saveToken(token:String?)
}