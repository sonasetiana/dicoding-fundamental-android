package com.sonasetiana.githubuser.presentation.modules.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.sonasetiana.githubuser.R
import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import com.sonasetiana.githubuser.databinding.FragmentDetailBinding
import com.sonasetiana.githubuser.helper.gone
import com.sonasetiana.githubuser.helper.visible
import com.sonasetiana.githubuser.presentation.factory.ViewModelFactory
import com.sonasetiana.githubuser.presentation.modules.detail.adapter.DetailPagerAdapter
import com.sonasetiana.githubuser.presentation.modules.main.MainActivity

class DetailFragment : Fragment(){

    private var binding : FragmentDetailBinding? = null

    private var viewModel : DetailViewModel? = null

    private var userData : UserData? = null

    private var detailUserData : DetailUserData? = null

    private val mActivity : MainActivity by lazy {
        activity as MainActivity
    }

    private var isFavorite: Boolean = false

    private var favoriteData : FavoriteData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.application?.let { application ->
            viewModel = ViewModelProvider(this, ViewModelFactory(application))[DetailViewModel::class.java]
        }
        DetailFragmentArgs.fromBundle(arguments as Bundle).data?.let {
            userData = it
        }
        mActivity.supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            requestDetailUser()
            fabFavorite.setOnClickListener {
                if (isFavorite) {
                    deleteFromFavorite()
                } else {
                    it.saveToFavorite()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun FragmentDetailBinding.requestDetailUser() {
        viewModel?.run {
            getDetailUser(username = userData?.login.orEmpty())
                .observe(viewLifecycleOwner) {
                    when (it) {
                        is NetworkResults.Loading -> {
                            if (it.isLoading) {
                                contentView.gone()
                                containerError.gone()
                                detailProgressBar.visible()
                            } else {
                                detailProgressBar.gone()
                            }
                        }
                        is NetworkResults.Success -> {
                            if (it.data != null) {
                                detailUserData = it.data
                                updateUi()
                            }else {
                                showErrorView("No Data", callback = {
                                    requestDetailUser()
                                })
                            }
                        }
                        is NetworkResults.Error -> {
                            showErrorView(it.apiError?.message.orEmpty(), callback = {
                                requestDetailUser()
                            })
                        }
                    }
                }
        }
    }

    private fun FragmentDetailBinding.updateUi() {
        detailUserData?.let { data ->
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            viewPager.adapter = DetailPagerAdapter(mActivity)
            TabLayoutMediator(tabLayout, viewPager) { tabs, index ->
                tabs.text = arrayListOf("Follower ${data.totalFollowerLabel}", "Following ${data.totalFollowingLabel}")[index]
            }.attach()
            Glide.with(avatar).load(data.avatarUrl).into(avatar)
            txtName.text = data.name
            txtLocation.text = data.location
            txtCompany.text = data.company
            data.checkFavorite()
        }
        contentView.visible()
    }

    private fun FragmentDetailBinding.showErrorView(message: String, callback: (()-> Unit)? = null) {
        containerError.visible()
        txtError.text = message
        btnTry.setOnClickListener {
            callback?.invoke()
        }
    }

    private fun DetailUserData.checkFavorite() {

        viewModel?.apply {
            checkIsFavorite(id).observe(viewLifecycleOwner){
                when (it) {
                    is RoomResults.Loading -> Unit

                    is RoomResults.Success -> {
                        val items = it.data as ArrayList
                        isFavorite = items.isNotEmpty()
                        val icon = if (isFavorite) {
                            favoriteData = items.first()
                            R.drawable.ic_round_favorite
                        } else {
                            R.drawable.ic_round_star
                        }
                        binding?.fabFavorite?.setImageResource(icon)
                    }

                    is RoomResults.Error -> {
                        isFavorite = false
                    }
                }
            }
        }
    }

    private fun View.saveToFavorite() {
        detailUserData?.let { user ->
            FavoriteData(
                userId = user.id,
                login = user.login,
                name = user.name,
                avatarUrl = user.avatarUrl,
                createdAt = user.created_at
            ).run {
                viewModel?.saveFavorite(this)
                Snackbar.make(this@saveToFavorite, "Berhasil ditambahkan ke favorit", Snackbar.LENGTH_LONG)
                    .setAction("Lihat Favorit"){
                        findNavController().navigate(R.id.action_detailFragment_to_favoriteFragment)
                    }.show()

            }
        }
    }

    private fun deleteFromFavorite() {
        favoriteData?.let { item ->
            viewModel?.deleteFavorite(item)
            Toast.makeText(activity, "${item.name} Berhasil dihapus dari favorit", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserData() = userData
}