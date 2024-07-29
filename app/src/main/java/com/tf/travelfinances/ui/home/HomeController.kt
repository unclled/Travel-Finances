package com.tf.travelfinances.ui.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider

class HomeController(
    private val fragment: HomeFragment,
    private val viewLifecycleOwner: LifecycleOwner
) {
    private lateinit var homeViewModel: HomeModel

    fun init() {
        homeViewModel = ViewModelProvider(fragment).get(HomeModel::class.java)
    }
}
