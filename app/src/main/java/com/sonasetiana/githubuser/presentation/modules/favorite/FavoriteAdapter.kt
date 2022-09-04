package com.sonasetiana.githubuser.presentation.modules.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sonasetiana.githubuser.data.local.loadImage
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val callback: ((FavoriteData) -> Unit) ? = null
) : RecyclerView.Adapter<FavoriteAdapter.Holder>(){

    private var items = ArrayList<FavoriteData>()

    @SuppressLint("NotifyDataSetChanged")
    fun set(newItems : ArrayList<FavoriteData>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemFavoriteBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    
    inner class Holder(private val binding : ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteData) = with(binding) {
            image.loadImage(item.avatarUrl)
            txtName.text = item.login
            btnFavorite.setOnClickListener { callback?.invoke(item) }
            with(itemView) {
                setOnClickListener {
                    val direction = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment()
                    direction.data = UserData(id = item.id, login = item.login, avatarUrl = item.avatarUrl)
                    findNavController().navigate(direction)
                }
            }
        }
    }
}