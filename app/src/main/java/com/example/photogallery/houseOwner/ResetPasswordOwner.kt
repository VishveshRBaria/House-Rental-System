package com.example.photogallery.houseOwner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.R
import com.example.photogallery.user.LoginUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordOwner : AppCompatActivity() {

    private lateinit var et_sendEmail: EditText
    private lateinit var btn_reset: Button

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass_owner)

        et_sendEmail = findViewById(R.id.et_sendEmail)
        btn_reset = findViewById(R.id.btn_reset)

        firebaseAuth = FirebaseAuth.getInstance()

        btn_reset.setOnClickListener {
            val email = et_sendEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this@ResetPasswordOwner, "Email is empty", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task: Task<Void> ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@ResetPasswordOwner, "Please Check Your Email", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@ResetPasswordOwner, LoginOwner::class.java))
                        } else {
                            val error = task.exception?.message
                            Toast.makeText(this@ResetPasswordOwner, error, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
