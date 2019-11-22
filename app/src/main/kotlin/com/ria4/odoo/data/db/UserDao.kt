package com.ria4.odoo.data.db

import androidx.room.*
import com.ria4.odoo.domain.entity.User

/**
 * Created by glovebx on 11.11.2019.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun loadAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: User): Int

    @Query("SELECT * FROM user WHERE current = 1 LIMIT 1")
    fun loadCurrentUser(): User

    @Query("UPDATE user SET current = 0 WHERE current = 1")
    fun deactivateCurrent()

}