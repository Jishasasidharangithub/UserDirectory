package com.example.userdirectory.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.userdirectory.databinding.LayoutErrorLoaderBinding
import com.example.userdirectory.network.NetworkResult

class ErrorLoaderView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var _binding: LayoutErrorLoaderBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = LayoutErrorLoaderBinding.inflate(
            LayoutInflater.from(context),
            this, true
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    fun updateUI(result: NetworkResult<*>?, showToast: Boolean = true) {
        when (result) {
            is NetworkResult.Loading -> {
                showView(ErrorLoader.LOADING, null)
            }

            is NetworkResult.Failure -> {
                when {
                    !isInternetAvailable(context) -> {
                        showView(ErrorLoader.NO_INTERNET, result)
                    }

                    result.code >= 500 -> {
                        if (result.code == 503) {
                            showView(ErrorLoader.NO_INTERNET, result)
                        } else {
                            showView(ErrorLoader.SERVER_ERROR, result)
                        }
                    }

                    else -> {
                        showView(null, null)
                        if (showToast) {
                            context.shortToast(result.message ?: "Something went wrong!")
                        }
                    }
                }
            }

            else -> {
                showView(null, null)
            }
        }
    }

    fun addRetryListener(listener: () -> Unit) {
        binding.btRetry.setOnClickListener { listener() }
    }

    @SuppressLint("SetTextI18n")
    private fun showView(type: ErrorLoader?, result: NetworkResult.Failure?) {
        if (type != ErrorLoader.LOADING) {
            binding.llProgress.hide()
        }

        binding.llProgress.hide()
        binding.llNetworkError.hide()
        binding.btRetry.hide()

        when (type) {
            ErrorLoader.LOADING -> {
                binding.llProgress.show()
            }

            ErrorLoader.NO_INTERNET -> {
                binding.llNetworkError.show()
                binding.btRetry.show()
                binding.tvTitle.text = "No internet connection!"
            }

            ErrorLoader.NO_RESULTS -> {

            }

            ErrorLoader.SERVER_ERROR -> {
                binding.llNetworkError.show()

                binding.tvTitle.text = "Something went wrong! Please try again later.\n[${result?.code?.toString() ?: ""}]"
            }

            null -> {}
        }
    }
}