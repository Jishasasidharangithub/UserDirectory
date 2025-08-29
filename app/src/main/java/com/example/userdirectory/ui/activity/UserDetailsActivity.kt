package com.example.userdirectory.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.userdirectory.R
import com.example.userdirectory.databinding.ActivityUserDetailsBinding
import com.example.userdirectory.databinding.ActivityUserListBinding
import com.example.userdirectory.model.response.UserListResponseItem
import com.example.userdirectory.network.NetworkResult
import com.example.userdirectory.ui.adapter.UserListAdapter
import com.example.userdirectory.ui.adapter.UserPostAdapter
import com.example.userdirectory.ui.viewmodel.UserViewModel
import com.example.userdirectory.utils.hide
import com.example.userdirectory.utils.shortToast
import com.example.userdirectory.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private val binding: ActivityUserDetailsBinding by lazy { ActivityUserDetailsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<UserViewModel>()
    private val userPostAdapter by lazy { UserPostAdapter() }

    private var user: UserListResponseItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        handleEvents()
        observeViewModel()
    }

    private fun init() {
        user = intent.getParcelableExtra<UserListResponseItem>("user_data")
        binding.apply {
            tvUserName.text = user?.username
            tvFirstName.text = user?.name
            tvEmail.text = user?.email
            tvPhone.text = user?.phone
            tvWebsite.text = user?.website
        }
        viewModel.usersPost(user?.id ?: -1)
        binding.rvPost.adapter = userPostAdapter
    }

    private fun handleEvents(){
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvPhone.setOnClickListener {
            val phone = binding.tvPhone.text.toString()
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(intent)
        }

        binding.tvEmail.setOnClickListener {
            val email = binding.tvEmail.text.toString()
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            startActivity(intent)
        }

        binding.tvWebsite.setOnClickListener {
            var website = binding.tvWebsite.text.toString()
            if (!website.startsWith("http://") && !website.startsWith("https://")) {
                website = "http://$website"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.usersPost.observe(this) { result ->
            loader(result)
            when (result) {
                is NetworkResult.Success -> {
                    binding.errorLoaderView.hide()
                    binding.rvPost.show()
                    userPostAdapter.submitList(result.data)
                }

                is NetworkResult.Failure -> {
                    binding.rvPost.hide()
                    binding.errorLoaderView.show()
                    shortToast(result.message)
                }

                is NetworkResult.Loading -> {
                    binding.rvPost.hide()
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
                        viewModel.usersPost(user?.id ?: -1) // or your retry logic
                    }
                }
            }
        } catch (e: Exception) {
            // Timber.e(e)
        }
    }
}