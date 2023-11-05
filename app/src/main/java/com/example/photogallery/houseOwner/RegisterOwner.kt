package com.example.photogallery.houseOwner

import  android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap
import java.util.regex.Pattern

class RegisterOwner : AppCompatActivity() {

    companion object {
        const val OWNER = "Owner"
    }

    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var et_confirmPassword: EditText
    private lateinit var et_username: EditText
    private lateinit var btn_Register: Button
    private lateinit var tv_loginBtn: TextView

    private val emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    private val pat = Pattern.compile(emailRegex)

    private lateinit var mAuth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_owner)

        et_username = findViewById(R.id.et_username)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_confirmPassword = findViewById(R.id.et_confirmPassword)
        btn_Register = findViewById(R.id.btn_register)
        tv_loginBtn = findViewById(R.id.tv_loginButton)

        mAuth = FirebaseAuth.getInstance()

        tv_loginBtn.setOnClickListener {
            startActivity(Intent(this@RegisterOwner, LoginOwner::class.java))
        }

        btn_Register.setOnClickListener {
            performAuth()
        }
    }

    private fun performAuth() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        val confirmPassword = et_confirmPassword.text.toString()
        val username = et_username.text.toString()

        when {
            email.isEmpty() -> {
                et_email.error = "Please Enter Email"
                return
            }

            !pat.matcher(email).matches() -> {
                et_email.error = "Please Enter a valid Email"
                return
            }

            password.isEmpty() -> {
                et_password.error = "Please input Password"
                return
            }

            password.length < 6 -> {
                et_password.error = "Password is too short"
                return
            }

            confirmPassword != password -> {
                et_confirmPassword.error = "Password doesn't match"
                return
            }

            else -> {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser = mAuth.currentUser
                            val userId = firebaseUser?.uid

                            if (userId != null) {
                                reference = FirebaseDatabase.getInstance().reference
                                    .child(OWNER)
                                    .child(userId)

                                val hashMap = HashMap<String, String>()
                                hashMap["id"] = userId
                                hashMap["username"] = username
                                hashMap["imageUrl"] = "default"
                                hashMap["search"] = username.toLowerCase()

                                reference.setValue(hashMap).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        sendUserToMainActivity()
                                    }
                                }
                            }

                            Toast.makeText(
                                this@RegisterOwner,
                                "Registration Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@RegisterOwner,
                                "Registration Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun sendUserToMainActivity() {
        val intent = Intent(this@RegisterOwner, DashboardOwner::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
