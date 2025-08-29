package io.proximety.hilitemall.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.proximety.hilitemall.databinding.ItemCouponBinding
import io.proximety.hilitemall.model.response.food_section.cart_list.Coupon

class CouponAdapter(private val listener: CouponClickListener) :
    ListAdapter<Coupon, CouponAdapter.ViewHolder>(CouponAdapterDiffCallback()) {

    inner class ViewHolder(private val binding: ItemCouponBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Coupon) {
            binding.apply {
                ivStoreIcon.load(item.logo)

                tvTitle.text = item.title
                tvCouponCode.text = item.code
                tvStoreName.text = item.restaurantName

                tvApply.setOnClickListener {
                    listener.onCouponClicked(item)
                }

                tvMoreInfo.setOnClickListener {
                    listener.onMoreInfo(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCouponBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CouponAdapterDiffCallback : DiffUtil.ItemCallback<Coupon>() {
        override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
            return oldItem == newItem
        }
    }

    interface CouponClickListener {
        fun onCouponClicked(coupon: Coupon)
        fun onMoreInfo(coupon: Coupon)
    }

}

