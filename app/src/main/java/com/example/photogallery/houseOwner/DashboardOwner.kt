package com.example.photogallery.houseOwner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.R

class DashboardOwner : AppCompatActivity() {

    private lateinit var btn_addHouse: Button
    private lateinit var btn_seeHouse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_dashboard_owner)

        btn_addHouse = findViewById(R.id.btn_addHouse)
        btn_seeHouse = findViewById(R.id.btn_seeHouse)

        btn_seeHouse.setOnClickListener {
            startActivity(Intent(this@DashboardOwner, SeeHouse::class.java))
        }

        btn_addHouse.setOnClickListener {
            startActivity(Intent(this@DashboardOwner, AddHouse::class.java))
        }
    }
}
