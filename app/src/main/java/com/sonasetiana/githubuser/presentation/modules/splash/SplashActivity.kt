package com.sonasetiana.githubuser.presentation.modules.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.sonasetiana.githubuser.databinding.ActivitySplashBinding
import com.sonasetiana.githubuser.presentation.factory.ViewModelFactory
import com.sonasetiana.githubuser.presentation.modules.main.MainActivity
import com.sonasetiana.githubuser.presentation.modules.setting.SettingViewModel

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    private val viewModel : SettingViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(application))[SettingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(viewModel) {
            getThemeSettings().observe(this@SplashActivity){
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        openMainActivity()
    }

    private fun openMainActivity() {
        Looper.getMainLooper()?.let {
            Handler(it).postDelayed({
              Intent(this, MainActivity::class.java)
                  .run {
                      startActivity(this)
                      finish()
                  }
            }, 2000L)
        }
    }
}