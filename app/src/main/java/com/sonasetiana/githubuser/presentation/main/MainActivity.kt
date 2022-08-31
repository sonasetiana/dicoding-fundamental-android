package com.sonasetiana.githubuser.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sonasetiana.githubuser.data.local.getGithubUsers
import com.sonasetiana.githubuser.databinding.ActivityMainBinding
import com.sonasetiana.githubuser.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val mainAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "GitHUb Users"

        with(binding) {
            rvUsers.adapter = mainAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        loadDataUser()
    }

    private fun loadDataUser() {
        mainAdapter.set(getGithubUsers())
    }
}