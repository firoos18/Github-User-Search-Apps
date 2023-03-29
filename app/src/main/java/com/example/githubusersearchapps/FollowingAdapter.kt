package com.example.githubusersearchapps

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FollowingAdapter(private val listUserName : List<String>, private val listUserId : List<Int>, private val avatarUrl : List<String>) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(username : String)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val avatarImg : ImageView = view.findViewById(R.id.imgPhoto)
        val tvName : TextView = view.findViewById(R.id.tv_item_name)
        val tvLoggin : TextView = view.findViewById(R.id.tv_item_loggin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        FollowingAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item_row, parent, false))

    override fun getItemCount(): Int = listUserName.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = listUserName[position]
        holder.tvLoggin.text = listUserId[position].toString()
        Glide.with(holder.avatarImg).load(avatarUrl[position]).into(holder.avatarImg)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUserName[holder.adapterPosition]) }
        holder.itemView.setOnClickListener {
            Log.d("USERNAMEFROMFOLLOWING", listUserName[holder.adapterPosition])
            val intentDetail = Intent(holder.itemView.context, UserDetailActivity::class.java)
            intentDetail.putExtra("username", listUserName[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}