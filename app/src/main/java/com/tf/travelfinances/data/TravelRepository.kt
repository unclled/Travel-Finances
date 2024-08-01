// TravelRepository.kt
package com.tf.travelfinances.data

import android.content.Context
import com.tf.travelfinances.Database.MainDB
import com.tf.travelfinances.Database.Travel
import kotlinx.coroutines.flow.Flow

class TravelRepository(context: Context) {
    private val travelDao = MainDB.getTravelDB(context).getDao()

    fun getTravels(): Flow<List<Travel>> {
        return travelDao.getTravels()
    }

    suspend fun addTravel(travel: Travel) {
        travelDao.addTravel(travel)
    }
}
