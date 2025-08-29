package com.nativ.nativapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nativ.nativapp.R
import com.nativ.nativapp.databinding.LayoutLoaderBinding
import com.nativ.nativapp.utils.hide
import com.nativ.nativapp.utils.show

class ErrorsAndLoaderView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {
    private var binding: LayoutLoaderBinding? = null

    init {
        binding = LayoutLoaderBinding.inflate(
            LayoutInflater.from(context),
            this, true
        )

        handleEvents()
    }

    private fun handleEvents() {
        binding?.root?.setOnClickListener {

        }
    }

    fun startLoaderAnimation() {
        binding?.loaderLottie?.show()
        binding?.loaderLottie?.playAnimation()
        binding?.includeNetwork?.root?.hide()
        binding?.includeServer?.root?.hide()
        binding?.includeEmpty?.root?.hide()
        binding?.includeLoyalityEmpty?.root?.hide()
    }

    fun stopLoaderAnimation() {
        binding?.loaderLottie?.playAnimation()
        binding?.loaderLottie?.hide()
    }

    fun showEmptyView(title: String, msg: String, buttonText:String,onButtonClick: () -> Unit ) {
        binding?.includeEmpty?.ivEmpty?.setImageResource(R.drawable.ic_empty)
        /*if (showEmptyImage) {
            binding?.includeEmpty?.ivEmpty?.show() // Show the empty image
        } else {
            binding?.includeEmpty?.ivEmpty?.hide() // Hide the empty image
        }*/
        binding?.includeEmpty?.tvTitle?.text = title
        binding?.includeEmpty?.tvSubTitle?.text = msg
        if (buttonText.isEmpty())
            binding?.includeEmpty?.tvAction?.hide()
        else {
            binding?.includeEmpty?.tvAction?.show()
            binding?.includeEmpty?.tvAction?.text = buttonText
            binding?.includeEmpty?.tvAction?.setOnClickListener {
                onButtonClick()
            }
        }
        binding?.includeEmpty?.root?.show()
        binding?.includeServer?.root?.hide()
        binding?.includeNetwork?.root?.hide()
        binding?.includeLoyalityEmpty?.root?.hide()
        binding?.loaderLottie?.hide()
    }

    fun showEmptyViewForAddress(title: String, msg: String, buttonText:String,showEmptyImage: Boolean,onButtonClick: () -> Unit ) {
        //binding?.includeEmpty?.ivEmpty?.setImageResource(R.drawable.ic_empty)
        if (showEmptyImage) {
            binding?.includeEmpty?.ivEmpty?.show() // Show the empty image
        } else {
            binding?.includeEmpty?.ivEmpty?.hide() // Hide the empty image
        }
        binding?.includeEmpty?.tvTitle?.text = title
        binding?.includeEmpty?.tvSubTitle?.text = msg
        if (buttonText.isEmpty())
            binding?.includeEmpty?.tvAction?.hide()
        else {
            binding?.includeEmpty?.tvAction?.show()
            binding?.includeEmpty?.tvAction?.text = buttonText
            binding?.includeEmpty?.tvAction?.setOnClickListener {
                onButtonClick()
            }
        }
        binding?.includeEmpty?.root?.show()
        binding?.includeServer?.root?.hide()
        binding?.includeNetwork?.root?.hide()
        binding?.includeLoyalityEmpty?.root?.hide()
        binding?.loaderLottie?.hide()
    }

    fun showEmptyLoyalityView(title: String, msg: String, buttonText:String,onButtonClick: () -> Unit ) {
        binding?.includeLoyalityEmpty?.ivEmptyLoyality?.setImageResource(R.drawable.ic_empty_loyalty)
        binding?.includeLoyalityEmpty?.tvTitle?.text = title
        binding?.includeLoyalityEmpty?.tvSubTitle?.text = msg
        binding?.includeLoyalityEmpty?.tvSubTitle?.setTextColor(resources.getColor(R.color.text_color2))
        binding?.includeLoyalityEmpty?.ivAction?.setImageResource(R.drawable.ic_back_arrow)
        binding?.includeLoyalityEmpty?.cslAction?.setOnClickListener {
                onButtonClick()
            }
        binding?.includeLoyalityEmpty?.root?.show()
        binding?.includeEmpty?.root?.hide()
        binding?.includeServer?.root?.hide()
        binding?.includeNetwork?.root?.hide()
        binding?.loaderLottie?.hide()
    }


    fun showCustomEmptyView(title: String, msg: String, drawable: Int) {
        binding?.includeEmpty?.ivEmpty?.setImageResource(drawable)
        binding?.includeEmpty?.tvTitle?.text = title
        binding?.includeEmpty?.tvSubTitle?.text = msg
        binding?.includeEmpty?.root?.show()
        binding?.includeServer?.root?.hide()
        binding?.includeNetwork?.root?.hide()
        binding?.includeLoyalityEmpty?.root?.hide()
        binding?.loaderLottie?.hide()
    }

    fun hideEmptyView() {
        binding?.includeEmpty?.root?.hide()
        binding?.includeServer?.root?.hide()
        binding?.includeNetwork?.root?.hide()
        binding?.includeLoyalityEmpty?.root?.hide()
        binding?.loaderLottie?.hide()
    }

    fun networkErrorView(onRetryClick: () -> Unit) {
        binding?.includeNetwork?.root?.show()
        binding?.includeServer?.root?.hide()
        binding?.includeEmpty?.root?.hide()
        binding?.includeLoyalityEmpty?.root?.hide()
        binding?.loaderLottie?.hide()

        binding?.includeNetwork?.tvRetry?.setOnClickListener {
            onRetryClick()
        }
    }

    fun serverErrorView(msg: String?,onRetryClick: () -> Unit) {
        binding?.includeServer?.root?.show()
        binding?.includeEmpty?.root?.hide()
        binding?.includeNetwork?.root?.hide()
        binding?.includeLoyalityEmpty?.root?.hide()
        binding?.loaderLottie?.hide()
        binding?.includeServer?.tvSubTitle?.text = msg

        binding?.includeServer?.tvRetry?.setOnClickListener {
           onRetryClick()
        }
    }
}