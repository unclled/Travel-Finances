package com.tf.travelfinances.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tf.travelfinances.Database.MainDB
import com.tf.travelfinances.databinding.FragmentHomeBinding
import com.tf.travelfinances.ui.new_travel.NewTravelView

class HomeView : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var database: MainDB? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()

        database = MainDB.getTravelDB(requireContext())
        viewModel.loadTravels(database!!)
    }

    private fun setupListeners() {
        binding.addNewTravel.setOnClickListener {
            createNewTravel()
        }
    }

    private fun createNewTravel() {
        val intent = Intent(requireContext(), NewTravelView::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
