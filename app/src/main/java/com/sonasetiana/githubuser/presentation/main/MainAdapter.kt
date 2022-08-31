package com.sonasetiana.githubuser.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sonasetiana.githubuser.data.UserData
import com.sonasetiana.githubuser.data.local.loadImage
import com.sonasetiana.githubuser.databinding.ItemMainBinding
import com.sonasetiana.githubuser.presentation.detail.DetailActivity

class MainAdapter : RecyclerView.Adapter<MainAdapter.Holder>(){

    private var items = ArrayList<UserData>()

    @SuppressLint("NotifyDataSetChanged")
    fun set(newItems : ArrayList<UserData>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemMainBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    
    inner class Holder(private val binding : ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserData) = with(binding) {
            image.loadImage(item.avatarUrl)
            txtName.text = item.login
            with(itemView) {
                setOnClickListener {
                    Intent(context, DetailActivity::class.java)
                        .apply {
                            putExtra("user", item)
                        }.run {
                            ContextCompat.startActivity(context, this, null)
                        }
                }
            }
        }
    }
}