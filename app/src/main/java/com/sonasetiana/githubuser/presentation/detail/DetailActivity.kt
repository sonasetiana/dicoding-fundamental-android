package com.sonasetiana.githubuser.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.sonasetiana.githubuser.data.DataModule
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.databinding.ActivityDetailBinding
import com.sonasetiana.githubuser.presentation.GithubViewModelFactory
import com.sonasetiana.githubuser.presentation.detail.adapter.DetailPagerAdapter

class DetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailBinding

    private var userData : UserData? = null

    private var detailUserData : DetailUserData? = null

    private val viewModel : DetailViewModel by lazy {
        ViewModelProvider(this, GithubViewModelFactory(DataModule.provideRepository()))[DetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initIntent()
        binding.observeViewModel()
        requestData()
    }

    private fun initIntent() {
        userData = intent?.getParcelableExtra("user")
    }

    private fun ActivityDetailBinding.updateUi() {
        detailUserData?.let { data ->
            toolbar.setNavigationOnClickListener { onBackPressed() }
            viewPager.adapter = DetailPagerAdapter(this@DetailActivity)
            TabLayoutMediator(tabLayout, viewPager) { tabs, index ->
                tabs.text = arrayListOf("Follower ${data.totalFollowerLabel}", "Following ${data.totalFollowingLabel}")[index]
            }.attach()
            Glide.with(avatar).load(data.avatarUrl).into(avatar)
            txtName.text = data.name
            txtLocation.text = data.location
            txtCompany.text = data.company
        }
    }

    private fun requestData() {
        viewModel.getDetailUser(userData?.login ?: "")
    }

    private fun ActivityDetailBinding.observeViewModel() {
        with(viewModel) {
            getIsLoading().observe(this@DetailActivity) {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    contentView.visibility = View.GONE
                    containerError.visibility = View.GONE
                }else  {
                    progressBar.visibility = View.GONE
                }
            }

            successGetDetailUser().observe(this@DetailActivity) {
                detailUserData = it
                updateUi()
                contentView.visibility = View.VISIBLE
            }

            errorGetDetailUser().observe(this@DetailActivity) {
                containerError.visibility = View.VISIBLE
                txtError.text = it
                btnTry.setOnClickListener {
                    requestData()
                }
            }
        }
    }

    fun getDetailViewModel() = viewModel

    fun getUserData() = userData

}