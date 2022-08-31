package com.sonasetiana.githubuser.presentation.splah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sonasetiana.githubuser.databinding.ActivitySplashBinding
import com.sonasetiana.githubuser.presentation.main.MainActivity

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
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