package com.sonasetiana.githubuser.presentation.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.sonasetiana.githubuser.data.DataModule
import com.sonasetiana.githubuser.databinding.ActivityMainBinding
import com.sonasetiana.githubuser.presentation.GithubViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val mainAdapter by lazy { MainAdapter() }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, GithubViewModelFactory(DataModule.provideRepository()))[MainViewModel::class.java]
    }

    private var keyword: String = ""

    private val debound = Runnable {
        if (keyword.isNotEmpty()) {
            viewModel.searchUser(keyword)
        } else {
            requestGetAllUsers()
        }
    }

    private var handler : Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "GitHUb Users"
        handler = Handler(Looper.getMainLooper())
        with(binding) {
            rvUsers.adapter = mainAdapter
            observeViewModel()
            etSearch.addTextChangedListener {
                handler?.removeCallbacks(debound)
                keyword = it.toString()
                tilSearch.endIconMode = if (keyword.isEmpty()) TextInputLayout.END_ICON_NONE else TextInputLayout.END_ICON_CUSTOM
                handler?.postDelayed(debound, 500L)
            }

            tilSearch.setEndIconOnClickListener {
                etSearch.text = null
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestGetAllUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(debound)
        handler = null
    }

    private fun requestGetAllUsers() = viewModel.getAllUsers()

    private fun ActivityMainBinding.observeViewModel() {
        with(viewModel) {
            getIsLoading().observe(this@MainActivity) {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    rvUsers.visibility = View.GONE
                    containerError.visibility = View.GONE
                }else  {
                    progressBar.visibility = View.GONE
                }
            }

            successGetAllUsers().observe(this@MainActivity) {
                mainAdapter.set(it)
                rvUsers.visibility = View.VISIBLE
            }

            successSearchUser().observe(this@MainActivity) {
                mainAdapter.set(it)
                rvUsers.visibility = View.VISIBLE
            }

            errorRequest().observe(this@MainActivity) {
                containerError.visibility = View.VISIBLE
                txtError.text = it
                btnTry.setOnClickListener {
                    getAllUsers()
                }
            }
        }
    }
}