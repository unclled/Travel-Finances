package com.tf.travelfinances.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Travel::class], version = 2)
@TypeConverters(Converters::class)
abstract class MainDB : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getTravelDB(context: Context): MainDB {
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