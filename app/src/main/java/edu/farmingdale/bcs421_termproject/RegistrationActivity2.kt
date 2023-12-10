package edu.farmingdale.bcs421_termproject

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.ArrayList
import java.util.Calendar

/**
 * The second registration activity to handle height/weight/age user input on intitial registration.
 */
class RegistrationActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    //private val intent = getIntent()
    //private val email = intent.getStringExtra("email")

    // The multipliers to be used in the Mifflin-St Jeor formula calculations.
    val sedentaryMultiplier = 1.2
    val slightlyActiveMultiplier = 1.375
    val lightlyActiveMultiplier = 1.425
    val moderatelyActiveMultiplier = 1.55
    val veryActiveMultiplier = 1.75

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity2)
        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        // Programatically adjust status bar color since we use multiple colors throughout the app
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.bb_blue)
        }

        val completeButton = findViewById<Button>(R.id.completeButton)
        val heightEditText = findViewById<EditText>(R.id.heightET)
        val weightEditText = findViewById<EditText>(R.id.weightET)
        val dobEditText = findViewById<EditText>(R.id.dobET)

        // Populate both spinners with the different options for sex and activity level.
        val sexDropDownMenu: Spinner = findViewById(R.id.sexSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.sex_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sexDropDownMenu.adapter = adapter
        }
        val activityDropDownMenu: Spinner = findViewById(R.id.activitySpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.activity_level_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            activityDropDownMenu.adapter = adapter
        }

        completeButton.setOnClickListener {
            val height = heightEditText.text.toString().trim()
            val weight = weightEditText.text.toString().trim()
            val dob = dobEditText.text.toString().trim()
            val sex = sexDropDownMenu.selectedItem.toString()
            val activityLevel = activityDropDownMenu.selectedItem
            Toast.makeText(this, "Activity level:"+activityLevel.toString(),Toast.LENGTH_LONG)

            // Check regex for date of birth first
//            if (email?.let { it1 -> checkDOB(it1) } == true) {
//                // Get the last 4 digits of DOB and do the math for the user's current age
//                val lastDigits = dob.takeLast(4).toInt()
//                val currentYear = Calendar.getInstance().get(Calendar.YEAR);
//                val age = currentYear - lastDigits
//
//                // Calculate calories and macros
//                //val listOfCaloriesAndMacros = calculateCaloriesMacros()
//                // Set up the hash map data to be added to Firestore
//                val data = hashMapOf(
//                    "height" to height,
//                    "weight" to weight,
//                    "age" to age,
//                    "sex" to sex,
//                    "calorie-goal" to 0,
//                    "protein-goal" to 0,
//                    "carbs-goal" to 0,
//                    "fat-goal" to 0
//                )
//            } else {
//                Toast.makeText(this, "Fill all fields in the correct format.", Toast.LENGTH_SHORT)
//            }

                        // Create a new user document with their unique email address as the name of the document
//                        db.collection("Users").document(email)
//                            .set(data)
//                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
//
//                        //  Go to dashboard
//                        startActivity(Intent(this, DashboardActivity::class.java))

        }
    }

    /**
     * Checks date of birth format. Must be MM/DD/YYYY. Returns true if the user inputted a valid format.
     */
    private fun checkDOB(dob: String): Boolean {
        // DOB must be in the format MM/DD/YY
        // We check for month by allowing a leading 0 for months 1-9 (september is 09 for example)
        // For october-december we allow 1 followed by a digit from 0-2
        // Then for days, we allow days from 01-31
        // Then for year, we take the last two digits to represent year (04 is 2004 for example)
        val dobPattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{2}$"
        return dob.matches(dobPattern.toRegex())
    }

    /**
     * Calculates the maintenance calories and macros for a male. Uses the Mifflin-St Jeor formula.
     */
    private fun calculateCaloriesMacros(height: Int, weight: Float, age: Int, sex: String) {

    }
}
