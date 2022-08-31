package com.sonasetiana.githubuser.presentation.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.databinding.ItemDetailBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.Holder>(){

    private var items = ArrayList<UserData>()

    @SuppressLint("NotifyDataSetChanged")
    fun set(newItems : ArrayList<UserData>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemDetailBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    
    inner class Holder(private val binding : ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserData) = with(binding) {
            Glide.with(avatar).load(item.avatarUrl).into(avatar)
            txtName.text = item.login
        }
    }
}