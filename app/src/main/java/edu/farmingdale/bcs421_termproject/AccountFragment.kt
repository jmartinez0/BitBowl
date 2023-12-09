package edu.farmingdale.bcs421_termproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.google.firebase.auth.FirebaseAuth
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
        val measurementsFragment = BodyMeasurementsFragment()
        val nutritionFragment = NutritionFragment()
        val goalsFragment = GoalsFragment()
        val progressFragment = ProgressFragment()
        val recipesFragment = RecipesFragment()
        val friendsFragment = FriendsFragment()
        val syncFragment = SyncFragment()

        personalInfoButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, personalInfoFragment)
                commit()
            }
        }

        bodyMeasurementsButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, measurementsFragment)
                commit()
            }
        }

        nutritionButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, nutritionFragment)
                commit()
            }
        }

        goalsButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, goalsFragment)
                commit()
            }
        }

        progressButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, progressFragment)
                commit()
            }
        }

        recipesButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, recipesFragment)
                commit()
            }
        }

        friendsButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, friendsFragment)
                commit()
            }
        }

        syncButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, syncFragment)
                commit()
            }
        }

        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }
        return view
    }

}