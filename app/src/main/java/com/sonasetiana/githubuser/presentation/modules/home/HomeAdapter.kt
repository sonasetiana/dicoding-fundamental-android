package com.sonasetiana.githubuser.presentation.modules.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sonasetiana.githubuser.data.local.loadImage
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.databinding.ItemMainBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.Holder>(){

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
                    val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                    directions.data = item
                    findNavController().navigate(directions)
                }
            }
        }
    }
}