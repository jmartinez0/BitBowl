package edu.farmingdale.bcs421_termproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import edu.farmingdale.bcs421_termproject.databinding.FragmentAccountBinding

private lateinit var binding: FragmentAccountBinding
class AccountFragment : Fragment(R.layout.fragment_account) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        var view :View = binding.root

        val personalInfoButton = view.findViewById<Button>(R.id.infoButton)
        val bodyMeasurementsButton = view.findViewById<Button>(R.id.measurementsButton)
        val nutritionButton = view.findViewById<Button>(R.id.nutritionButton)
        val goalsButton = view.findViewById<Button>(R.id.goalsButton)
        val progressButton = view.findViewById<Button>(R.id.progressButton)
        val recipesButton = view.findViewById<Button>(R.id.recipesButton)
        val friendsButton = view.findViewById<Button>(R.id.friendsButton)
        val syncButton = view.findViewById<Button>(R.id.syncButton)
        val logOutButton = view.findViewById<Button>(R.id.logOutButton)

        val personalInfoFragment = PersonalInformationFragment()
        //val measurementsFragment = x()
        //val nutritionFragment = x()
        //val goalsFragment = x()
        //val progressFragment = x()
        //val recipesFragment = x()
        //val friendsFragment = x()
        //val syncFragment = x()

        personalInfoButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        bodyMeasurementsButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        nutritionButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        goalsButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        progressButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        recipesButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        friendsButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        syncButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        logOutButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }
        return view
    }

}