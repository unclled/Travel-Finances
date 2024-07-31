// NewTravelActivity.kt
package com.tf.travelfinances.ui.new_travel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

        binding.buttonPlus.setOnClickListener {
            var peopleForSplit = binding.peopleForSplit.text.toString().toInt()
            if (peopleForSplit < 9) {
                peopleForSplit++
                binding.peopleForSplit.setText(peopleForSplit.toString())
                addNewHuman(peopleForSplit)
            }
        }

        binding.buttonMinus.setOnClickListener {
            var peopleForSplit = binding.peopleForSplit.text.toString().toInt()
            if (peopleForSplit > 2) {
                peopleForSplit--
                binding.peopleForSplit.setText(peopleForSplit.toString())
                removeLastHuman()
            }
        }
    }

    private fun removeLastHuman() {
        val peopleList = findViewById<LinearLayout>(R.id.peopleList)
        peopleList.removeView(peopleList.getChildAt(peopleList.childCount - 1))
    }

    private fun addNewHuman(peopleForSplit: Int) {
        val contextThemeWrapper = ContextThemeWrapper(
            this,
            com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox
        )

        val existingHuman = findViewById<TextInputLayout>(R.id.human2)

        val newHumanLayout = TextInputLayout(contextThemeWrapper).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16, 0, 0)
            }
            hint = existingHuman.hint
            boxBackgroundMode = existingHuman.boxBackgroundMode
            boxStrokeColor = existingHuman.boxStrokeColor
            endIconMode = existingHuman.endIconMode
            id = View.generateViewId()
            tag = "human$peopleForSplit"
        }

        val existingEditText = existingHuman.editText!!
        val newHumanEditText = TextInputEditText(contextThemeWrapper).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            hint = existingEditText.hint
            inputType = existingEditText.inputType
            background = existingEditText.background
            setText("Человек $peopleForSplit")
        }

        newHumanLayout.addView(newHumanEditText)

        val peopleList = findViewById<LinearLayout>(R.id.peopleList)
        peopleList.addView(newHumanLayout)
    }

}
