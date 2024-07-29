// NewTravelViewModel.kt
package com.tf.travelfinances.ui.new_travel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tf.travelfinances.Database.Travel
import com.tf.travelfinances.data.TravelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewTravelViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TravelRepository = TravelRepository(application)

    val travels: Flow<List<Travel>> = repository.getTravels()

    fun addTravel(travel: Travel) {
        viewModelScope.launch {
            repository.addTravel(travel)
        }
    }
}
