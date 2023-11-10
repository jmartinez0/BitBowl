package edu.farmingdale.bcs421_termproject;

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HealthConnectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.healthconnect_activity)



        // Allow button which will allow BitBowl to read and write from Health Connect
        val allowButton = findViewById < Button > (R.id.allowButton)
    }

}
