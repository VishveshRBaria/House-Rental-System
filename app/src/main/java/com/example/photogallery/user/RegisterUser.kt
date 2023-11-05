package com.example.photogallery.user

import android.content.Intent
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

class RegisterUser : AppCompatActivity() {

    companion object {
        const val DATABASE_USERS = "Users"
    }

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLoginBtn: TextView

    private val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    private val emailPattern = Pattern.compile(emailRegex)

    private lateinit var mAuth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_owner)

        etUsername = findViewById(R.id.et_username)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirmPassword)
        btnRegister = findViewById(R.id.btn_register)
        tvLoginBtn = findViewById(R.id.tv_loginButton)

        mAuth = FirebaseAuth.getInstance()

        tvLoginBtn.setOnClickListener {
            startActivity(Intent(this@RegisterUser, LoginUser::class.java))
        }

        btnRegister.setOnClickListener {
            performRegistration()
        }
    }

    private fun performRegistration() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()
        val username = etUsername.text.toString()

        when {
            email.isEmpty() -> {
                etEmail.error = "Please Enter Email"
                return
            }

            !emailPattern.matcher(email).matches() -> {
                etEmail.error = "Please Enter a valid Email"
                return
            }

            password.isEmpty() -> {
                etPassword.error = "Please input Password"
                return
            }

            password.length < 6 -> {
                etPassword.error = "Password is too short"
                return
            }

            confirmPassword != password -> {
                etConfirmPassword.error = "Password doesn't match"
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
                                    .child(DATABASE_USERS)
                                    .child(userId)

                                val userMap = HashMap<String, String>()
                                userMap["id"] = userId
                                userMap["username"] = username
                                userMap["imageUrl"] = "default"
                                userMap["search"] = username.toLowerCase()

                                reference.setValue(userMap).addOnCompleteListener { registrationTask ->
                                    if (registrationTask.isSuccessful) {
                                        sendUserToMainActivity()
                                    }
                                }
                            }

                            Toast.makeText(
                                this@RegisterUser,
                                "Registration Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@RegisterUser,
                                "Registration Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun sendUserToMainActivity() {
        val intent = Intent(this@RegisterUser, DashboardUser::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
