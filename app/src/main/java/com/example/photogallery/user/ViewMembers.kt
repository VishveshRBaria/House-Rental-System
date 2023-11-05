package com.example.photogallery.user

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.photogallery.MemberModel
import com.example.photogallery.R
import com.example.photogallery.adapter.SeeMemberAdapterOwner
import com.example.photogallery.houseOwner.RegisterOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ViewMembers : AppCompatActivity() {

    private var houseId: String? = null
    private var userId: String? = null
    private lateinit var rv_showAllFood: RecyclerView
    private lateinit var adapter: SeeMemberAdapterOwner
    private val mList: ArrayList<MemberModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_members2)

        val intent = intent
        houseId = intent.getStringExtra("houseId")
        val noOfRoom = intent.getStringExtra("noOfRoom")
        val rentPerRoom = intent.getStringExtra("rentPerRoom")
        val houseDescription = intent.getStringExtra("houseDescription")
        val houseLocation = intent.getStringExtra("houseLocation")
        val houseImage = intent.getStringExtra("houseImage")
        userId = intent.getStringExtra("userId")

        rv_showAllFood = findViewById(R.id.recyclerView)
        rv_showAllFood.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        rv_showAllFood.layoutManager = linearLayoutManager
        getAllArticle()
    }

    private fun getAllArticle() {
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (!firebaseUser?.uid.isNullOrEmpty()) {
            val reference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference().child("member")
                    .child(userId!!).child(houseId!!)
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    mList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val article: MemberModel? = dataSnapshot.getValue(MemberModel::class.java)
                        article?.let { mList.add(it) }
                    }
                    Log.d("TAG1", "onDataChange: " + mList.get(0).name)
                    adapter = SeeMemberAdapterOwner(this@ViewMembers, mList)
                    rv_showAllFood.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {}

            })
        }
    }
}
