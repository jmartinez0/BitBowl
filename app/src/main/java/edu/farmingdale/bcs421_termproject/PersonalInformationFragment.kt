package edu.farmingdale.bcs421_termproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import edu.farmingdale.bcs421_termproject.databinding.FragmentAccountBinding
import edu.farmingdale.bcs421_termproject.databinding.FragmentPersonalInformationBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding: FragmentPersonalInformationBinding
class PersonalInformationFragment : Fragment(R.layout.fragment_personal_information) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalInformationBinding.inflate(layoutInflater, container, false)
        var view : View = binding.root

        val dobEditText = view.findViewById<EditText>(R.id.dobET)

        // Drop down menu for selecting male/female
        val sexDropDownMenu: Spinner = view.findViewById(R.id.sexSpinner)
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.sex_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sexDropDownMenu.adapter = adapter
        }

        // Code to pull date of birth data from Firestore and update dobEditText
        // Code to pull sex data from Firestore and update sexDropDownMenu

        return view
    }

}