package com.example.photogallery.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.photogallery.R

class HouseDetails : AppCompatActivity() {

    private lateinit var tv_houseDesc: TextView
    private lateinit var iv_houseImage: ImageView
    private lateinit var btn_viewMember: Button
    private lateinit var btn_viewLocation: Button
    private lateinit var btn_call: Button
    private lateinit var btn_message: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_details2)

        val intent = intent
        val houseId = intent.getStringExtra("houseId")
        val noOfRoom = intent.getStringExtra("noOfRoom")
        val rentPerRoom = intent.getStringExtra("rentPerRoom")
        val houseDescription = intent.getStringExtra("houseDescription")
        val houseLocation = intent.getStringExtra("houseLocation")
        val houseImage = intent.getStringExtra("houseImage")
        val userId = intent.getStringExtra("userId")

        tv_houseDesc = findViewById(R.id.tv_houseDesc)
        iv_houseImage = findViewById(R.id.iv_houseImage)
        btn_viewMember = findViewById(R.id.btn_viewMember)
        btn_viewLocation = findViewById(R.id.btn_viewLocation)
        btn_call = findViewById(R.id.btn_call)
        btn_message = findViewById(R.id.btn_message)

        btn_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:8950562633")
            startActivity(intent)
        }

        btn_message.setOnClickListener {
            val smsIntent = Intent(Intent.ACTION_VIEW)
            smsIntent.type = "vnd.android-dir/mms-sms"
            smsIntent.putExtra("address", "8950562633")
            smsIntent.putExtra("sms_body", "Message")
            startActivity(smsIntent)
        }

        tv_houseDesc.text = houseDescription
        Glide.with(this).load(houseImage).into(iv_houseImage)

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
