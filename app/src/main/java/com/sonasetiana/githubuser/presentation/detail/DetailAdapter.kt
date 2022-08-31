package com.sonasetiana.githubuser.presentation.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.databinding.ItemDetailBinding

class ItemDetail(
    var label: String,
    var value: String
)

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.Holder>(){

    private var items = ArrayList<ItemDetail>()

    @SuppressLint("NotifyDataSetChanged")
    fun set(data : DetailUserData) {
        this.items = arrayListOf(
            ItemDetail(label = "Username", value = data.name),
            ItemDetail(label = "Location", value = data.location),
            ItemDetail(label = "Followers", value = data.followers.toString()),
            ItemDetail(label = "Following", value = data.following.toString()),
            ItemDetail(label = "Repository", value = data.publicRepos.toString()),
            ItemDetail(label = "Company", value = data.company),
            ItemDetail(label = "Blog", value = data.blog),
            ItemDetail(label = "Created At", value = data.created_at),
        )
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
        fun bind(item: ItemDetail) = with(binding) {
            txtLabel.text = item.label
            txtValue.text = item.value
        }
    }
}