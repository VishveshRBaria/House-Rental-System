package com.example.photogallery.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogallery.HouseModel
import com.example.photogallery.R
import com.example.photogallery.houseOwner.HouseDetails

class SeeHouseAdapterUser(
    private val context: Context,
    private val houseModelArrayList: ArrayList<HouseModel>
) : RecyclerView.Adapter<SeeHouseAdapterUser.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.seehouse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = houseModelArrayList[position]

        holder.tvLocation.text = model.houseLocation
        holder.tvRentPerRoom.text = model.rentPerRoom
        holder.tvNoOfRoom.text = model.noOfRoom
        Glide.with(context).load(model.houseImage).into(holder.ivHouseImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, HouseDetails::class.java)

            intent.putExtra("houseId", model.houseId)
            intent.putExtra("noOfRoom", model.noOfRoom)
            intent.putExtra("rentPerRoom", model.rentPerRoom)
            intent.putExtra("houseDescription", model.houseDescription)
            intent.putExtra("houseLocation", model.houseLocation)
            intent.putExtra("houseImage", model.houseImage)
            intent.putExtra("userId", model.userId)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return houseModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivHouseImage: ImageView = itemView.findViewById(R.id.imageview)
        val tvNoOfRoom: TextView = itemView.findViewById(R.id.tv_noOfRooms)
        val tvRentPerRoom: TextView = itemView.findViewById(R.id.tv_rentPerRoom)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_location)
    }
}
