package edu.farmingdale.bcs421_termproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import edu.farmingdale.bcs421_termproject.databinding.FragmentBodyMeasurementsBinding
import edu.farmingdale.bcs421_termproject.databinding.FragmentPersonalInformationBinding

class BodyMeasurementsFragment : Fragment(R.layout.fragment_body_measurements) {

    private lateinit var binding: FragmentBodyMeasurementsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBodyMeasurementsBinding.inflate(layoutInflater, container, false)
        var view : View = binding.root
        val accountFragment = AccountFragment()
        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, accountFragment)
                commit()
            }
        }

        // Code to pull the user's height and weight data from Firestore.

        val heightEditText = view.findViewById<EditText>(R.id.heightET)
        val weightEditText = view.findViewById<EditText>(R.id.weightET)
        val editHeight = view.findViewById<ImageView>(R.id.editHeight)
        val editWeight = view.findViewById<ImageView>(R.id.editWeight)

        // Click listeners for the image views to make the edit texts editable (they shouldn't be at first).
        // Code to confirm changes and update Firestore.
        return view
    }

}