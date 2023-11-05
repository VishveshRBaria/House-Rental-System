package com.example.photogallery.houseOwner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

class AddMember : AppCompatActivity() {

    private lateinit var et_memberName: EditText
    private lateinit var et_memberJob: EditText
    private lateinit var et_memberAge: EditText
    private lateinit var et_memberRent: EditText
    private lateinit var et_memberJoiningDate: EditText
    private lateinit var et_memberPhoneNumber: EditText
    private lateinit var btn_addMember: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)

        val intent = intent
        val houseId = intent.getStringExtra("houseId")
        val ownerId = intent.getStringExtra("ownerId")

        et_memberAge = findViewById(R.id.et_memberAge)
        et_memberName = findViewById(R.id.et_memberName)
        et_memberJob = findViewById(R.id.et_memberJob)
        et_memberRent = findViewById(R.id.et_memberRent)
        et_memberJoiningDate = findViewById(R.id.et_memberJoiningDate)
        et_memberPhoneNumber = findViewById(R.id.et_memberPhoneNumber)
        btn_addMember = findViewById(R.id.btn_addMember)

        btn_addMember.setOnClickListener(View.OnClickListener {
            val age = et_memberAge.text.toString()
            val name = et_memberName.text.toString()
            val job = et_memberJob.text.toString()
            val rent = et_memberRent.text.toString()
            val joiningDate = et_memberJoiningDate.text.toString()
            val phoneNumber = et_memberPhoneNumber.text.toString()

            createMember(age, name, job, rent, joiningDate, phoneNumber, ownerId, houseId)
        })
    }


    private fun createMember(
        age: String,
        name: String,
        job: String,
        rent: String,
        joiningDate: String,
        phoneNumber: String,
        ownerId: String? = null,
        houseId: String?
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser?.uid

        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("members")
        val hashMap: HashMap<String, String?> = HashMap()
        hashMap["houseId"] = houseId
        hashMap["age"] = age
        hashMap["name"] = name
        hashMap["job"] = job
        hashMap["rent"] = rent
        hashMap["joiningDate"] = joiningDate
        hashMap["phoneNumber"] = phoneNumber
        hashMap["ownerId"] = ownerId
        reference.push().setValue(hashMap)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@AddMember, "Member Added Successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@AddMember, "Member Added Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
