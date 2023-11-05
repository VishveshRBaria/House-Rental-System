package com.example.photogallery.houseOwner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.HouseModel
import com.example.photogallery.R
import com.example.photogallery.adapter.SeeHouseAdapterOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SeeHouse : AppCompatActivity() {

    private lateinit var rv_showAllFood: RecyclerView
    private lateinit var adapter: SeeHouseAdapterOwner
    private val mList: ArrayList<HouseModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_house)

        rv_showAllFood = findViewById(R.id.recyclerView)
        rv_showAllFood.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        rv_showAllFood.layoutManager = linearLayoutManager
        getAllArticle()
    }

    private fun getAllArticle() {
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val userId: String? = firebaseUser?.uid
        userId?.let {
            val reference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference().child("upload").child(it)
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    mList.clear()
                    for (dataSnapshot1 in dataSnapshot.children) {
                        val article: HouseModel? = dataSnapshot1.getValue(HouseModel::class.java)
                        article?.let { mList.add(it) }
                    }
                    adapter = SeeHouseAdapterOwner(this@SeeHouse, mList)
                    rv_showAllFood.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle onCancelled
                }
            })
        }
    }
}
