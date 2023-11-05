package com.example.photogallery.user

import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.HouseModel
import com.example.photogallery.R
import com.example.photogallery.adapter.SeeHouseAdapterUser
import com.example.photogallery.houseOwner.RegisterOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class DashboardUser : AppCompatActivity() {

    private lateinit var rvShowAllFood: RecyclerView
    private lateinit var adapter: SeeHouseAdapterUser
    private val mList = ArrayList<HouseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_dashboard_user)

        rvShowAllFood = findViewById(R.id.recyclerView)
        rvShowAllFood.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        rvShowAllFood.layoutManager = linearLayoutManager
        getAllArticle()
    }

    private fun getAllArticle() {
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        firebaseUser?.uid?.let { userId ->
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("upload")
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    mList.clear()
                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        for (dataSnapshot1: DataSnapshot in dataSnapshot.children) {
                            val article: HouseModel? = dataSnapshot1.getValue(HouseModel::class.java)
                            article?.let { mList.add(it) }
                        }
                    }
                    Log.d("TAG1", "onDataChange: " + mList.getOrNull(0))
                    adapter = SeeHouseAdapterUser(this@DashboardUser, mList)
                    rvShowAllFood.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the onCancelled event if needed
                }
            })
        }
    }
}