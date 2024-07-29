// NewTravelActivity.kt
package com.tf.travelfinances.ui.new_travel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tf.travelfinances.R
import com.tf.travelfinances.Database.Travel
import com.tf.travelfinances.databinding.NewTravelBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewTravelView : AppCompatActivity() {

    private lateinit var binding: NewTravelBinding
    private val viewModel: NewTravelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewTravelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()

        lifecycleScope.launch {
            viewModel.travels.collect { travels ->
                // Обновляем UI с данными о путешествиях
            }
        }
    }

    private fun setupListeners() {
        binding.buttonSave.setOnClickListener {
            val travel = Travel(
                name = binding.travelName.editText?.text.toString(),
                departure = binding.departure.text.toString().toLong(),
                arrival = binding.arrival.text.toString().toLong(),
                splitExpenses = binding.splitExpenses.isChecked,
                peopleForSplit = binding.peopleForSplit.text.toString().toInt()
            )
            viewModel.addTravel(travel)
        }
    }
}
