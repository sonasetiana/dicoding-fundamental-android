package com.sonasetiana.githubuser.presentation.modules.detail.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.presentation.modules.detail.fragments.FollowerFragment
import com.sonasetiana.githubuser.presentation.modules.detail.fragments.FollowingFragment

class DetailPagerAdapter(activity: AppCompatActivity, data: UserData?) : FragmentStateAdapter(activity) {

    private val fragments = arrayListOf(
        FollowerFragment.newInstance(data),
        FollowingFragment.newInstance(data)
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}