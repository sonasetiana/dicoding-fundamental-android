package com.sonasetiana.githubuser.presentation.modules.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import com.sonasetiana.githubuser.databinding.FollowerFragmentBinding
import com.sonasetiana.githubuser.helper.gone
import com.sonasetiana.githubuser.helper.visible
import com.sonasetiana.githubuser.presentation.factory.ViewModelFactory
import com.sonasetiana.githubuser.presentation.modules.detail.DetailViewModel
import com.sonasetiana.githubuser.presentation.modules.detail.adapter.DetailAdapter

class FollowerFragment : Fragment() {

    companion object {
        private val EXTRA_DATA = "data"
        fun newInstance(data: UserData?): FollowerFragment {
            val args = Bundle()
            data?.let { args.putParcelable(EXTRA_DATA, it) }
            val fragment = FollowerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var binding : FollowerFragmentBinding? = null

    private val detailAdapter : DetailAdapter by lazy {
        DetailAdapter()
    }

    private var viewModel : DetailViewModel? = null

    private var userData : UserData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.application?.let { application ->
            viewModel = ViewModelProvider(this, ViewModelFactory(application))[DetailViewModel::class.java]
        }
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
        userData = arguments?.getParcelable(EXTRA_DATA) as? UserData
        binding?.apply {
            with(rvFollower) {
                adapter = detailAdapter
                val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                addItemDecoration(decoration)
            }
            requestFollower()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun FollowerFragmentBinding.requestFollower() {
        viewModel?.run {
            getFollower(username = userData?.login.orEmpty())
                .observe(viewLifecycleOwner) {
                    when (it) {
                        is NetworkResults.Loading -> {
                            if (it.isLoading) {
                                rvFollower.gone()
                                containerError.gone()
                                progressBar.visible()
                            } else {
                                progressBar.gone()
                            }
                        }
                        is NetworkResults.Success -> {
                            val items = it.data as ArrayList
                            if (items.isNotEmpty()) {
                                detailAdapter.set(items)
                                rvFollower.visible()
                            }else {
                                showErrorView("No Data", callback = {
                                    requestFollower()
                                })
                            }
                        }
                        is NetworkResults.Error -> {
                            showErrorView(it.apiError?.message.orEmpty(), callback = {
                                requestFollower()
                            })
                        }
                    }
                }
        }
    }

    private fun FollowerFragmentBinding.showErrorView(message: String, callback: (()-> Unit)? = null) {
        containerError.visible()
        txtError.text = message
        btnTry.setOnClickListener {
            callback?.invoke()
        }
    }
}