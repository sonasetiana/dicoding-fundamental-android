package com.sonasetiana.githubuser.presentation.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import com.sonasetiana.githubuser.databinding.FragmentHomeBinding
import com.sonasetiana.githubuser.helper.gone
import com.sonasetiana.githubuser.helper.visible
import com.sonasetiana.githubuser.presentation.factory.ViewModelFactory
import com.sonasetiana.githubuser.presentation.modules.main.MainActivity

class HomeFragment : Fragment(){

    private var binding : FragmentHomeBinding? = null

    private var viewModel : HomeViewModel? = null

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter()
    }

    private val mActivity : MainActivity by lazy {
        activity as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.application?.let { application ->
            viewModel = ViewModelProvider(this, ViewModelFactory(application))[HomeViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMainViewModel()
        binding?.apply {
            with(rvUsers) {
                adapter = homeAdapter
                setHasFixedSize(true)
            }
            observeAllData()
            observeSearchUser()
        }
    }

    override fun onResume() {
        super.onResume()
        mActivity.supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun observeMainViewModel() {
        with(mActivity.getMainViewModel()) {
            onTextChange().observe(mActivity){
                viewModel?.searching(it)
            }
        }
    }

    private fun FragmentHomeBinding.observeAllData() {
        viewModel?.run {
            getAllUsers().observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResults.Loading -> {
                        if (it.isLoading) {
                            rvUsers.gone()
                            containerError.gone()
                            progressBar.visible()
                        } else  {
                            progressBar.gone()
                        }
                    }
                    is NetworkResults.Success -> {
                        val items = it.data as ArrayList
                        if (items.isNotEmpty()) {
                            homeAdapter.set(items)
                            rvUsers.visible()
                        } else {
                            showErrorView("No Data", callback = {
                                getAllUsers()
                            })
                        }
                    }
                    is NetworkResults.Error -> {
                        showErrorView(it.apiError?.message.orEmpty(), callback = {
                            requestAllUsers()
                        })
                    }
                }
            }
            requestAllUsers()
        }
    }

    private fun FragmentHomeBinding.observeSearchUser() {
        viewModel?.run {
            successSearchUser().observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResults.Loading -> {
                        if (it.isLoading) {
                            rvUsers.gone()
                            containerError.gone()
                            progressBar.visible()
                        } else  {
                            progressBar.gone()
                        }
                    }
                    is NetworkResults.Success -> {
                        val items = it.data as ArrayList
                        if (items.isNotEmpty()) {
                            homeAdapter.set(items)
                            rvUsers.visible()
                        } else {
                            showErrorView("No Data", callback = {
                                requestSearch()
                            })
                        }
                    }
                    is NetworkResults.Error -> {
                        showErrorView(it.apiError?.message.orEmpty(), callback = {
                            observeSearchUser()
                        })
                    }
                }
            }
        }
    }

    private fun FragmentHomeBinding.showErrorView(message: String, callback: (()-> Unit)? = null) {
        containerError.visible()
        txtError.text = message
        btnTry.setOnClickListener {
            callback?.invoke()
        }
    }
}