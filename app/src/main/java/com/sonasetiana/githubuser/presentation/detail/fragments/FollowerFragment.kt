package com.sonasetiana.githubuser.presentation.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonasetiana.githubuser.databinding.FollowerFragmentBinding
import com.sonasetiana.githubuser.presentation.detail.DetailActivity
import com.sonasetiana.githubuser.presentation.detail.adapter.DetailAdapter

class FollowerFragment : Fragment() {

    private var binding : FollowerFragmentBinding? = null

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
        binding = FollowerFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            with(rvFollower) {
                adapter = detailAdapter
                val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                addItemDecoration(decoration)
            }
            observeViewModel()
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
            getDetailViewModel().getFollower(username = user?.login ?: "")
        }
    }

    private fun FollowerFragmentBinding.observeViewModel() {
        with(detailActivity.getDetailViewModel()) {
            loadingGetFollower().observe(viewLifecycleOwner){
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    rvFollower.visibility = View.GONE
                    containerError.visibility = View.GONE
                }else  {
                    progressBar.visibility = View.GONE
                }
            }

            successGetFollower().observe(viewLifecycleOwner){
                detailAdapter.set(it)
                rvFollower.visibility = View.VISIBLE
            }

            errorGetFollower().observe(viewLifecycleOwner){
                containerError.visibility = View.VISIBLE
                txtError.text = it
                btnTry.setOnClickListener {
                    requestData()
                }
            }
        }
    }
}