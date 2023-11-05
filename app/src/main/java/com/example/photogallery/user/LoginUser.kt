package com.example.photogallery.user

//import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.regex.Pattern

class LoginUser : AppCompatActivity() {

    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_login: Button
    private lateinit var tv_registerBtn: TextView
    private lateinit var tv_forgotPassword: TextView

    private val emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    private val pat = Pattern.compile(emailRegex)

//    private lateinit var progressDialog: ProgressDialog

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)
        tv_registerBtn = findViewById(R.id.tv_registerButton)
        tv_forgotPassword = findViewById(R.id.tv_forgotPassword)

        tv_forgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginUser, ResetPasswordUser::class.java))
        }

//        progressDialog = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!

        btn_login.setOnClickListener {
            performLogin()
        }

        tv_registerBtn.setOnClickListener {
            startActivity(Intent(this@LoginUser, RegisterUser::class.java))
        }
    }

    private fun performLogin() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        when {
            email.isEmpty() -> {
                et_email.setError("Please Enter Email")
                return
            }
            !pat.matcher(email).matches() -> {
                et_email.setError("Please Enter a valid Email")
                return
            }
            password.isEmpty() -> {
                et_password.setError("Please input Password")
                return
            }
            password.length < 6 -> {
                et_password.setError("Password too short")
                return
            }
            else -> {
//                progressDialog.setMessage("Login in to your Account....")
//                progressDialog.setTitle("Loading")
//                progressDialog.setCanceledOnTouchOutside(false)
//                progressDialog.show()

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
//                            progressDialog.dismiss()
                            sendUserToMainActivity()
                            Toast.makeText(this@LoginUser, "Login Successful", Toast.LENGTH_SHORT).show()
                        } else {
//                            progressDialog.dismiss()
                            Toast.makeText(this@LoginUser, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun sendUserToMainActivity() {
        val intent = Intent(this@LoginUser, DashboardUser::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
