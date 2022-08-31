package com.sonasetiana.githubuser.presentation.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonasetiana.githubuser.databinding.FollowingFragmentBinding
import com.sonasetiana.githubuser.presentation.detail.DetailActivity
import com.sonasetiana.githubuser.presentation.detail.adapter.DetailAdapter

class FollowingFragment : Fragment() {

    private var binding : FollowingFragmentBinding? = null

    private val detailAdapter : DetailAdapter by lazy {
        DetailAdapter()
    }

    private val detailActivity : DetailActivity by lazy {
        activity as DetailActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FollowingFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            with(rvFollowing) {
                adapter = detailAdapter
                val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                addItemDecoration(decoration)
            }
            observeViewModel()
        }

        detailAdapter.addCallback {
            detailActivity.getDetailViewModel().getDetailUser(username = it.login)
        }

        requestData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun requestData() {
        with(detailActivity) {
            val user = getUserData()
            getDetailViewModel().getFollowing(username = user?.login ?: "")
        }

    }

    private fun FollowingFragmentBinding.observeViewModel() {
        with(detailActivity.getDetailViewModel()) {
            loadingGetFollowing().observe(viewLifecycleOwner){
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    rvFollowing.visibility = View.GONE
                    containerError.visibility = View.GONE
                }else  {
                    progressBar.visibility = View.GONE
                }
            }

            successGetFollowing().observe(viewLifecycleOwner){
                detailAdapter.set(it)
                rvFollowing.visibility = View.VISIBLE
            }

            errorGetFollowing().observe(viewLifecycleOwner){
                containerError.visibility = View.VISIBLE
                txtError.text = it
                btnTry.setOnClickListener {
                    requestData()
                }
            }
        }
    }
}