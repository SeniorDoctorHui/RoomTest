package com.hyejeanmoon.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(version = 1, entities = [User::class])
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "user_database.db"

        private var INSTANCE: AppDatabase? = null
        private var lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
                }

                return INSTANCE!!
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    abstract fun getUserDao(): UserDao
}