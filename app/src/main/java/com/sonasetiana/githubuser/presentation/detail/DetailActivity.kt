package com.sonasetiana.githubuser.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sonasetiana.githubuser.data.local.getDetailGithubUser
import com.sonasetiana.githubuser.data.local.loadCircleImage
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailBinding

    private var userData : UserData? = null

    private val detailAdapter by lazy { DetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initIntent()
        with(binding) {
            rvDetail.adapter = detailAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        getDetailUser()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initIntent() {
        userData = intent?.getParcelableExtra<UserData>("user")
        setupActionBar()
    }

    private fun setupActionBar() {
        userData?.let {
            supportActionBar?.apply {
                title = it.login
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    private fun getDetailUser() {
        userData?.let { user ->
            getDetailGithubUser(userId = user.id)?.let { item ->
                with(binding) {
                    image.loadCircleImage(item.avatarUrl)
                    detailAdapter.set(item)
                }
            }
        }

    }
}