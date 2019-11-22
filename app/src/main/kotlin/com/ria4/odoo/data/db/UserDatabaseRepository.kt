package com.ria4.odoo.data.db

import com.ria4.odoo.domain.database.repository.UserDaoRepository
import com.ria4.odoo.domain.entity.User
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by glovebx on 06/01/18.
 */
class UserDatabaseRepository @Inject internal constructor(private val userDao: UserDao) : UserDaoRepository {
    override fun loadCurrentUser(): Single<User> {
        // 为什么用Single，因为loadCurrentUser返回null时Single会抛出错误，而Flowable不会做后续处理
        // just()方法无论如何都只会在当前线程里执行。所以即使看上去有异步的过程，但其实这是个同步的过程
        return Single.fromCallable<User> {  userDao.loadCurrentUser() }
    }

    override fun updateUser(user: User): Observable<Int> {
        return Observable.just(userDao.update(user))
    }

    override fun isUserRepoEmpty(): Observable<Boolean> = Observable.fromCallable{ userDao.loadAll().isEmpty() }

    override fun insertUsers(users: List<User>): Observable<List<Long>> {
        return Observable.fromCallable {
            if (users.run { any { it.current } }) {
                // 数据库中其他的current需要设置为false
                userDao.deactivateCurrent()
            }
            userDao.insertAll(users)
        }
    }

    override fun loadUsers(): Observable<List<User>> = Observable.fromCallable{ userDao.loadAll() }
}