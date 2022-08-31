package com.sonasetiana.githubuser.presentation.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.sonasetiana.githubuser.R
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
        supportActionBar?.title = "GitHUb"
        handler = Handler(Looper.getMainLooper())
        with(binding) {
            rvUsers.adapter = mainAdapter
            observeViewModel()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = "Search users"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                /*
                    Gunakan method ini ketika search selesai atau OK
                 */
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                /*
                    Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
                 */
                override fun onQueryTextChange(param: String?): Boolean {
                    handler?.removeCallbacks(debound)
                    keyword = param ?: ""
                    handler?.postDelayed(debound, 500L)
                    return true
                }

            })
        }

        return true
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