// NewTravelActivity.kt
package com.tf.travelfinances.ui.new_travel

import NewTravelViewModel
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tf.travelfinances.Database.MainDB
import com.tf.travelfinances.R
import com.tf.travelfinances.databinding.NewTravelBinding
import kotlinx.coroutines.launch

class NewTravelView : AppCompatActivity() {

    private lateinit var binding: NewTravelBinding
    private val viewModel: NewTravelViewModel by viewModels()
    private var database: MainDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewTravelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        //this.deleteDatabase("travels.db");
        database = MainDB.getTravelDB(this)
    }

    private fun setupListeners() {
        binding.buttonSave.setOnClickListener {
            exportTravelData()
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

        binding.splitExpenses.setOnClickListener {
            showHide(binding.splitBox)
            showHide(binding.peopleList)
        }
    }

    private fun exportTravelData() {
        val humanNames = mutableListOf<String>()
        val name = binding.travelName.editText?.text.toString()
        val departure = binding.departure.text.toString()
        val arrival = binding.arrival.text.toString()
        val splitExpenses = binding.splitExpenses.isChecked
        val peopleForSplit = binding.peopleForSplit.text.toString().toIntOrNull() ?: 1

        when {
            name.isBlank() -> {
                showWarning("Укажите название поездки!")
                return
            }

            departure.isBlank() -> {
                showWarning("Укажите дату отъезда!")
                return
            }

            arrival.isBlank() -> {
                showWarning("Укажите дату прибытия!")
                return
            }

            viewModel.isValidDate(arrival, departure) == 0 -> {
                showWarning("Укажите корректную дату отъезда!")
                return
            }

            viewModel.isValidDate(arrival, departure) == -1 -> {
                showWarning("Укажите корректную дату прибытия!")
                return
            }

            viewModel.isValidDate(arrival, departure) == -2 -> {
                showWarning("Ошибка преобразования даты, попробуйте еще раз!")
                return
            }
        }
        if (binding.splitExpenses.isChecked) {
            for (i in 0 until binding.peopleList.childCount) {
                val child = binding.peopleList.getChildAt(i)
                if (child is TextInputLayout) {
                    val editText = child.editText
                    val name = editText?.text.toString()
                    if (name.isNotBlank()) {
                        humanNames.add(name)
                    } else {
                        showWarning("Заполните все поля с именами!")
                        humanNames.clear()
                        return
                    }
                }
            }
        }
        viewModel.addTravel(
            name,
            departure,
            arrival,
            splitExpenses,
            peopleForSplit,
            humanNames,
            database!!
        )
        this.finish()
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

    private fun showWarning(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showHide(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}
