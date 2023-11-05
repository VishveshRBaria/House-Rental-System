package com.example.photogallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.MemberModel
import com.example.photogallery.R

class SeeMemberAdapterOwner(
    private val context: Context,
    private val arrayList: ArrayList<MemberModel>
) : RecyclerView.Adapter<SeeMemberAdapterOwner.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.seemember, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]

        holder.tvName.text = model.name
        holder.tvJoiningDate.text = model.joiningDate
        holder.tvPhoneNumber.text = model.phoneNumber
        holder.tvAge.text = model.age
        holder.tvJob.text = model.job
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_memberName)
        val tvJoiningDate: TextView = itemView.findViewById(R.id.tv_memberJoiningDate)
        val tvPhoneNumber: TextView = itemView.findViewById(R.id.tv_memberPhoneNumber)
        val tvAge: TextView = itemView.findViewById(R.id.tv_memberAge)
        val tvJob: TextView = itemView.findViewById(R.id.tv_memberJob)
    }
}
