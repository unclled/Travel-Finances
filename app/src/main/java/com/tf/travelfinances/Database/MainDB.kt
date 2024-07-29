package com.tf.travelfinances.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Travel::class], version = 1)
abstract class MainDB : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getTravelData(context: Context): MainDB {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                "travels.db"
            ).build()
        }

        fun saveTravelData(travel: Travel) {

        }
    }
}