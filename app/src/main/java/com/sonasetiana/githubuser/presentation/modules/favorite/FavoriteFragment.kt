package com.sonasetiana.githubuser.presentation.modules.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.databinding.FragmentFavoriteBinding
import com.sonasetiana.githubuser.helper.gone
import com.sonasetiana.githubuser.helper.visible
import com.sonasetiana.githubuser.presentation.factory.ViewModelFactory
import com.sonasetiana.githubuser.presentation.modules.main.MainActivity

class FavoriteFragment : Fragment(){

    private var binding : FragmentFavoriteBinding? = null

    private lateinit var favoriteAdapter: FavoriteAdapter

    private var viewModel : FavoriteViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.application?.let { application ->
            viewModel = ViewModelProvider(this, ViewModelFactory(application))[FavoriteViewModel::class.java]
        }

        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdapter { item ->
            viewModel?.apply {
                deleteFavorite(item)
                //getFavorites()
                Toast.makeText(activity, "${item.name} berhasil dihapus dari favorit", Toast.LENGTH_SHORT).show()
            }
        }

        binding?.apply {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            with(rvFavorite) {
                adapter = favoriteAdapter
                setHasFixedSize(true)
            }
            getFavorites()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun FragmentFavoriteBinding.getFavorites() {

        viewModel?.apply {
            getFavorites().observe(viewLifecycleOwner){
                when (it) {
                    is RoomResults.Loading -> {
                        if (it.isLoading) {
                            progressBar.visible()
                            rvFavorite.gone()
                            txtError.gone()
                        } else  {
                            progressBar.gone()
                        }
                    }

                    is RoomResults.Success -> {
                        val items = it.data as ArrayList
                        if (items.isNotEmpty()) {
                            favoriteAdapter.set(items)
                            rvFavorite.visible()
                        } else {
                            txtError.visible()
                        }
                    }

                    is RoomResults.Error -> {
                        txtError.visible()
                    }
                }
            }
        }
    }
}