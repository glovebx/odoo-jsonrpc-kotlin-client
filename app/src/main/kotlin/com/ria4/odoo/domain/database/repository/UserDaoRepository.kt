package com.ria4.odoo.domain.database.repository

import com.ria4.odoo.domain.entity.User
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by glovebx on 06/01/18.
 */
interface UserDaoRepository {

    fun isUserRepoEmpty(): Observable<Boolean>

    fun insertUsers(users: List<User>): Observable<List<Long>>

    fun updateUser(user: User): Observable<Int>

    fun loadUsers(): Observable<List<User>>

    fun loadCurrentUser(): Single<User>

}