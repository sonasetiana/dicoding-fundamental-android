package com.sonasetiana.githubuser.presentation.modules.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sonasetiana.githubuser.R
import com.sonasetiana.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "GitHUb"
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
                override fun onQueryTextChange(keyword: String?): Boolean {
                    with(viewModel) {
                        setTextOnChange(keyword.orEmpty())
                    }
                    return true
                }

            })
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            findNavController(R.id.container).navigate(R.id.action_homeFragment_to_favoriteFragment)
            return true
        }
        if (item.itemId == R.id.setting) {
            findNavController(R.id.container).navigate(R.id.action_homeFragment_to_settingFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun getMainViewModel() = viewModel
}