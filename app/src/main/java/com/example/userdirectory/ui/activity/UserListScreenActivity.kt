package com.example.userdirectory.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.userdirectory.databinding.ActivityUserListBinding
import com.example.userdirectory.model.response.UserListResponseItem
import com.example.userdirectory.network.NetworkResult
import com.example.userdirectory.ui.adapter.UserListAdapter
import com.example.userdirectory.ui.viewmodel.UserViewModel
import com.example.userdirectory.utils.hide
import com.example.userdirectory.utils.shortToast
import com.example.userdirectory.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListScreenActivity : AppCompatActivity() {

    private val binding: ActivityUserListBinding by lazy {
        ActivityUserListBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel by viewModels<UserViewModel>()
    private val userListAdapter by lazy { UserListAdapter(userCallback) }

    private val userCallback = object : UserListAdapter.ItemClickListener {
        override fun onDetailClick(user: UserListResponseItem) {
            val intent = Intent(this@UserListScreenActivity, UserDetailsActivity::class.java)
            intent.putExtra("user_data", user)
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        observeViewModel()
    }

    private fun init() {
        viewModel.usersList()
        binding.rvUser.adapter = userListAdapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.usersList()
        }
    }

    private fun observeViewModel() {
        viewModel.usersList.observe(this) { result ->
            loader(result)
            binding.swipeRefresh.isRefreshing = false

            when (result) {
                is NetworkResult.Success -> {
                    binding.errorLoaderView.hide()
                    binding.rvUser.show()
                    userListAdapter.submitList(result.data)
                }

                is NetworkResult.Failure -> {
                    binding.rvUser.hide()
                    binding.errorLoaderView.show()
                    shortToast(result.message)
                }

                is NetworkResult.Loading -> {
                    binding.rvUser.hide()
                    binding.errorLoaderView.show()
                }
            }
        }
    }


    fun loader(result: NetworkResult<*>?, showToast: Boolean = true) {
        try {
            binding.errorLoaderView.let { errorLoaderView ->
                errorLoaderView.updateUI(result, showToast)

                if (result is NetworkResult.Failure) {
                    errorLoaderView.addRetryListener {
                        viewModel.usersList() // or your retry logic
                    }
                }
            }
        } catch (e: Exception) {
            // Timber.e(e)
        }
    }

}