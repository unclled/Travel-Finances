package com.tf.travelfinances.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    fun addTravel(travel: Travel)
    @Query("SELECT * FROM travels")
    fun getTravels(): Flow<List<Travel>>
}