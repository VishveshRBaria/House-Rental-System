package com.example.photogallery

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.databinding.ActivityMainBinding
import com.example.photogallery.houseOwner.RegisterOwner
import com.example.photogallery.user.RegisterUser

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnHouseOwner.setOnClickListener {
            startActivity(Intent(this,RegisterOwner::class.java))
            finish()
        }

        binding.btnUser.setOnClickListener {
            startActivity(Intent(this,RegisterUser::class.java))
            finish()
        }






    }
}
