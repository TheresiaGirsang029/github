package com.dicoding.github.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.github.R
import de.hdodenhof.circleimageview.CircleImageView

class AdapterUser : RecyclerView.Adapter<AdapterUser.ListViewHolder>() {
    private var data: MutableList<User> = mutableListOf()

    fun setData(userData: MutableList<User>){
        data = userData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUser.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterUser.ListViewHolder, position: Int) {
        val user = data[position]
        Glide.with(holder.itemView.context)
            .load(user.avatar_url)
            .apply(RequestOptions().override(150, 150))
            .into(holder.ivUser)
        holder.tvUsername.text = user.login
        holder.clickUser.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailUser::class.java)
            intent.putExtra("login",user.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivUser: CircleImageView= itemView.findViewById(R.id.img_avatar)
        var tvUsername: TextView = itemView.findViewById(R.id.txt_name)
        var clickUser : LinearLayout = itemView.findViewById(R.id.chooseuser)
    }
}