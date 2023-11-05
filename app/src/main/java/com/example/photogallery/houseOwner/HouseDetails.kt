package com.example.photogallery.houseOwner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.photogallery.R

class HouseDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_details)

        val intent = intent
        val houseId = intent.getStringExtra("houseId")
        val noOfRoom = intent.getStringExtra("noOfRoom")
        val rentPerRoom = intent.getStringExtra("rentPerRoom")
        val houseDescription = intent.getStringExtra("houseDescription")
        val houseLocation = intent.getStringExtra("houseLocation")
        val houseImage = intent.getStringExtra("houseImage")
        val userId = intent.getStringExtra("userId")

        val tv_houseDesc: TextView = findViewById(R.id.tv_houseDesc)
        val iv_houseImage: ImageView = findViewById(R.id.iv_houseImage)
        val btn_addMember: Button = findViewById(R.id.btn_addMember)
        val btn_viewMember: Button = findViewById(R.id.btn_viewMember)
        val btn_viewLocation: Button = findViewById(R.id.btn_viewLocation)

        tv_houseDesc.text = houseDescription
        Glide.with(this).load(houseImage).into(iv_houseImage)

        btn_addMember.setOnClickListener {
            val intent1 = Intent(this, AddMember::class.java)
            intent1.putExtra("houseId", houseId)
            intent1.putExtra("noOfRoom", noOfRoom)
            intent1.putExtra("rentPerRoom", rentPerRoom)
            intent1.putExtra("houseDescription", houseDescription)
            intent1.putExtra("houseLocation", houseLocation)
            intent1.putExtra("houseImage", houseImage)
            intent1.putExtra("userId", userId)
            startActivity(intent1)
        }

        btn_viewLocation.setOnClickListener {
            val intent1 = Intent(this, ViewLocation::class.java)
            intent1.putExtra("houseId", houseId)
            intent1.putExtra("noOfRoom", noOfRoom)
            intent1.putExtra("rentPerRoom", rentPerRoom)
            intent1.putExtra("houseDescription", houseDescription)
            intent1.putExtra("houseLocation", houseLocation)
            intent1.putExtra("houseImage", houseImage)
            intent1.putExtra("userId", userId)
            startActivity(intent1)
        }

        btn_viewMember.setOnClickListener {
            val intent1 = Intent(this, ViewMembers::class.java)
            intent1.putExtra("houseId", houseId)
            intent1.putExtra("noOfRoom", noOfRoom)
            intent1.putExtra("rentPerRoom", rentPerRoom)
            intent1.putExtra("houseDescription", houseDescription)
            intent1.putExtra("houseLocation", houseLocation)
            intent1.putExtra("houseImage", houseImage)
            intent1.putExtra("userId", userId)
            startActivity(intent1)
        }
    }
}
