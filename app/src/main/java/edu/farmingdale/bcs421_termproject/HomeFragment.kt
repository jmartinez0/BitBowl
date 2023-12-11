package edu.farmingdale.bcs421_termproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import edu.farmingdale.bcs421_termproject.databinding.FragmentHomeBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar

private lateinit var binding: FragmentHomeBinding

/**
 * Home fragment to display a user's calorie and macro summary.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val cal = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("MM-dd-yyyy")
    val todaysDateFormatted = dateFormat.format(cal.time) // Today's date formatted for the Firebase methods
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

        lifecycleScope.launch {
            // Retrieve all necessary values for displaying a user's summary
            val calorieGoal = retrieveCalorieGoal()
            val proteinGoal = retrieveProteinGoal()
            val carbsGoal = retrieveCarbsGoal()
            val fatGoal = retrieveFatGoal()
            val stepsGoal = retrieveStepGoal()
            val caloriesConsumed = retrieveCaloriesConsumed()
            val caloriesBurned = retrieveCaloriesBurned()
            val proteinConsumed = retrieveProteinConsumed()
            val carbsConsumed = retrieveCarbsConsumed()
            val fatConsumed = retrieveFatConsumed()
            val stepsToday = retrieveSteps()
            val exerciseTimeToday = retrieveExerciseTime()

            Log.d("$calorieGoal", "Calorie Goal")
            Log.d("$proteinGoal", "Protein Goal")
            Log.d("$carbsGoal", "Carbs Goal")
            Log.d("$fatGoal", "Fat Goal")
            Log.d("$stepsGoal", "Steps Goal")
            Log.d("$caloriesConsumed", "Calories Consumed")
            Log.d("$caloriesBurned", "Calories Burned")
            Log.d("$proteinConsumed", "Protein Consumed")
            Log.d("$carbsConsumed", "Carbs Consumed")
            Log.d("$fatConsumed", "Fat Consumed")
            Log.d("$stepsToday", "Steps Today")
            Log.d("$exerciseTimeToday", "Exercise Time Today")

            // Calculate calories, macros, and steps remaining for the day
            val caloriesRemaining = calorieGoal - caloriesConsumed + caloriesBurned
            Log.d("$caloriesRemaining", "Calories Remaining")
            val proteinRemaining = proteinGoal - proteinConsumed
            val carbsRemaining = carbsGoal - carbsConsumed
            val fatRemaining = fatGoal - fatConsumed

            // Create percentages for calories, macros, and steps to fill up the progress bars accordingly
            val percentOfCaloriesComplete = caloriesConsumed / calorieGoal
            val percentOfProteinComplete = proteinConsumed / proteinGoal
            val percentOfCarbsComplete = carbsConsumed / carbsGoal
            val percentOfFatComplete = fatConsumed / fatGoal
            val percentOfStepsComplete = stepsToday / stepsGoal

            // Update the UI
            caloriesProgressCircle.progress = percentOfCaloriesComplete
            caloriesText.text = caloriesRemaining.toString() + "\nremaining"
            proteinProgressCircle.progress = percentOfProteinComplete
            proteinText.text = proteinRemaining.toString() + "g\nremaining"
            carbsProgressCircle.progress = percentOfCarbsComplete
            carbsText.text = carbsRemaining.toString() + "g\nremaining"
            fatProgressCircle.progress = percentOfFatComplete
            fatText.text = fatRemaining.toString() + "g\nremaining"
            stepsProgressBar.progress = percentOfStepsComplete
            stepsAmount.text = stepsToday.toString() + "/" + stepsGoal.toString() + " steps"

            goalCalories.text = calorieGoal.toString()
            foodCalories.text = caloriesConsumed.toString()
            exerciseCalories.text = caloriesBurned.toString()

            exerciseCal.text = caloriesBurned.toString() + " cal"
            exerciseTime.text = exerciseTimeToday.toString()
        }
        return view
    }

    /**
     * Returns the user's set calorie goal.
     */
    private suspend fun retrieveCalorieGoal(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString())
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("calorie-goal")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's set protein goal.
     */
    private suspend fun retrieveProteinGoal(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString())
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("protein-goal")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's set carbohydrates goal.
     */
    private suspend fun retrieveCarbsGoal(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString())
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("carbs-goal")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's set fat goal.
     */
    private suspend fun retrieveFatGoal(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString())
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("fat-goal")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's set steps goal.
     */
    private suspend fun retrieveStepGoal(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString())
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("steps-goal")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's calories consumed so far for the day.
     */
    private suspend fun retrieveCaloriesConsumed(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString()).collection("Progress").document(todaysDateFormatted)
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("calories-today")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's calories burned so far for the day.
     */
    private suspend fun retrieveCaloriesBurned(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString()).collection("Progress").document(todaysDateFormatted)
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("calories-burned-today")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's protein consumed so far for the day.
     */
    private suspend fun retrieveProteinConsumed(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString()).collection("Progress").document(todaysDateFormatted)
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("protein-today")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's carbohydrates consumed so far for the day.
     */
    private suspend fun retrieveCarbsConsumed(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString()).collection("Progress").document(todaysDateFormatted)
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("carbs-today")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's fat consumed so far for the day.
     */
    private suspend fun retrieveFatConsumed(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString()).collection("Progress").document(todaysDateFormatted)
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("fat-today")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's steps so far for the day.
     */
    private suspend fun retrieveSteps(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString()).collection("Progress").document(todaysDateFormatted)
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("steps-today")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Returns the user's time spent exercising so far for the day.
     */
    private suspend fun retrieveExerciseTime(): Int {
        val docRef = db.collection("Users").document(auth.currentUser?.email.toString()).collection("Progress").document(todaysDateFormatted)
        return try {
            val docSnapshot = docRef.get().await()
            docSnapshot.getLong("exercise-time-today")?.toInt() ?: 0
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            0
        }
    }

}