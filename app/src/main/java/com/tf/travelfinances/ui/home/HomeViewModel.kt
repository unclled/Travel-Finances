package com.tf.travelfinances.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.room.Database
import com.tf.travelfinances.Database.MainDB
import com.tf.travelfinances.databinding.ActivityMainBinding

class HomeViewModel(application: Application) : AndroidViewModel(application){

    fun loadTravels(database: MainDB) {
        Log.e(database.getDao().getTravels().toString(), "travels")
    }

}
