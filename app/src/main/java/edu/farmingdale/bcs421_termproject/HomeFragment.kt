package edu.farmingdale.bcs421_termproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import edu.farmingdale.bcs421_termproject.databinding.FragmentHomeBinding

private lateinit var binding: FragmentHomeBinding

/**
 * Home fragment to display a user's calorie and macro summary.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        var view :View = binding.root

        // Calories card
        val caloriesProgressCircle = view.findViewById<ProgressBar>(R.id.caloriesProgress)
        val caloriesText = view.findViewById<TextView>(R.id.caloriesProgressTV)
        val goalCalories = view.findViewById<TextView>(R.id.calorieGoalAmountTV)
        val foodCalories = view.findViewById<TextView>(R.id.foodAmountTV)
        val exerciseCalories = view.findViewById<TextView>(R.id.exerciseAmountTV)


        // Macros card
        val proteinProgressCircle = view.findViewById<ProgressBar>(R.id.proteinProgress)
        val proteinText = view.findViewById<TextView>(R.id.proteinProgressTV)
        val carbsProgressCircle = view.findViewById<ProgressBar>(R.id.carbsProgress)
        val carbsText = view.findViewById<TextView>(R.id.carbsProgressTV)
        val fatProgressCircle = view.findViewById<ProgressBar>(R.id.fatProgress)
        val fatText = view.findViewById<TextView>(R.id.fatProgressTV)

        // Steps card
        val stepsAmount = view.findViewById<TextView>(R.id.stepsAmountTV)
        val stepsProgressBar = view.findViewById<ProgressBar>(R.id.stepsProgress)

        // Exercise card
        val exerciseCal = view.findViewById<TextView>(R.id.caloriesBurnedTV)
        val exerciseTime = view.findViewById<TextView>(R.id.exerciseTimeTV)

        return view
    }

}