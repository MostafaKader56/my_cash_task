package com.bb4first.mycashtask.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bb4first.mycashtask.databinding.CardTrendingItemBinding
import com.bb4first.mycashtask.model.home.trendingSellersResponse.TrendingItem
import com.bumptech.glide.Glide

class HomeTrendingAdapter(
    private var items: List<TrendingItem> = listOf(),
    private var onItemClicked: OnCategoryClicked? = null,
) : RecyclerView.Adapter<HomeTrendingAdapter.HomeTrendingViewHolder>() {
    fun setOnDepartmentItemClicked(onItemClicked: OnCategoryClicked) {
        this.onItemClicked = onItemClicked
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(newItems: List<TrendingItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeTrendingViewHolder(
        CardTrendingItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeTrendingViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class HomeTrendingViewHolder(val binding: CardTrendingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked?.onClicked(items[bindingAdapterPosition], bindingAdapterPosition)
            }
        }

        fun bind(item: TrendingItem) {
            Glide
                .with(binding.root.context)
                .load(item.image)
                .into(binding.imgTrending)
        }
    }


    interface OnCategoryClicked {
        fun onClicked(
            item: TrendingItem, selectedIndex: Int
        )
    }
}