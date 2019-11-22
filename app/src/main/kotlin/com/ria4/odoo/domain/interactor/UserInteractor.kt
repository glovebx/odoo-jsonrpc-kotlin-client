package com.ria4.odoo.domain.interactor

import androidx.annotation.NonNull
import com.ria4.odoo.data.request.CommonRPCRequest
import com.ria4.odoo.data.request.DbRPCRequest
import com.ria4.odoo.di.scope.PerActivity
import com.ria4.odoo.domain.database.repository.UserDaoRepository
import com.ria4.odoo.domain.entity.ServerVersion
import com.ria4.odoo.domain.entity.User
import com.ria4.odoo.domain.repository.CommonRepository
import com.ria4.odoo.domain.repository.UserRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by glovebx on 11.11.2019.
 */
@PerActivity
class UserInteractor @Inject constructor(private val commonDataRepository: CommonRepository
                                         , private val userDataRepository: UserRepository
                                         , private val userDaoRepository: UserDaoRepository) {

//    fun getUser(code: String): Flowable<User> {
//        return userDataRepository.getToken(code)
//                .doOnNext { userDataRepository.saveToken(it.token) }
//                .flatMap { userDataRepository.getUser() }
//                .doOnNext { userDataRepository.logIn() }
//    }
//
//    fun init(): Flowable<Array<String>> {
//        return getServerVersion()
//                .doOnNext { it.protocolVersion }
//                .flatMap { listDb() }
//                .doOnNext {  }
//    }

    fun getServerVersion(): Flowable<ServerVersion> {
        val request = CommonRPCRequest("version")
        return commonDataRepository.getServerVersion(request)
    }

    fun listDb(): Flowable<Array<String>> {
        val request = DbRPCRequest("list")
        return commonDataRepository.listDb(request)
    }

    fun authenticate(@NonNull db: String, @NonNull userName: String, @NonNull password: String): Flowable<User> {
        val request = CommonRPCRequest("authenticate")
        request.args[0] = db
        request.args[1] = userName
        request.args[2] = password
        // 必须参数
        request.args[3] = mapOf<String, String>()
        return userDataRepository.authenticate(request)
    }
//
//    fun getUser(@NonNull cookie: String): Flowable<User> {
//        return userDataRepository.getUser(cookie)
//    }

    fun logOut() {
        clearCache()
        userDataRepository.clearLoginData()
    }

    fun clearCache() {
        userDataRepository.clearCache()
    }

    fun loadCurrentUser(): Single<User> = userDaoRepository.loadCurrentUser()

    fun saveUsers(users: List<User>) = userDaoRepository.insertUsers(users)
}