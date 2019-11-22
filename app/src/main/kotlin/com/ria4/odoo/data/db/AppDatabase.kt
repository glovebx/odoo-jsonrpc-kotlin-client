package com.ria4.odoo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ria4.odoo.domain.entity.User

/**
 * Created by glovebx on 11.11.2019.
 */
@Database(entities = [(User::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}