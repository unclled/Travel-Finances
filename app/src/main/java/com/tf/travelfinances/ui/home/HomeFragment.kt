package com.tf.travelfinances.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tf.travelfinances.databinding.FragmentHomeBinding
import com.tf.travelfinances.ui.new_travel.NewTravelView
import com.tf.travelfinances.ui.new_travel.NewTravelViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: HomeController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        controller = HomeController(this, viewLifecycleOwner)
        controller.init()

        // Убедитесь, что метод newTravelClick зарегистрирован для LinearLayout
        binding.addNewTravel.setOnClickListener {
            newTravelClick()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Метод для обработки нажатий
    private fun newTravelClick() {
        createNewTravel()
    }

    private fun createNewTravel() {
        val intent = Intent(requireContext(), NewTravelView::class.java)
        startActivity(intent)
        Toast.makeText(context, "New Travel Clicked", Toast.LENGTH_SHORT).show()
    }

}
