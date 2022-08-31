package com.sonasetiana.githubuser.presentation.detail.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sonasetiana.githubuser.presentation.detail.fragments.FollowerFragment
import com.sonasetiana.githubuser.presentation.detail.fragments.FollowingFragment

class DetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val fragments = arrayListOf(
        FollowerFragment(),
        FollowingFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}