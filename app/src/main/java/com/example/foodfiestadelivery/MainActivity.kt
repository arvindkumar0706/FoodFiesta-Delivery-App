package com.example.foodfiestadelivery

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btn=findViewById<AppCompatButton>(R.id.ordersBtn)

        btn.setOnClickListener {
            val intent=Intent(this,OrdersActivity::class.java)
            startActivity(intent)
        }

    }
}