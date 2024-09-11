package com.bb4first.mycashtask.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.databinding.CardPopularItemBinding
import com.bb4first.mycashtask.model.home.popularSellersResponse.PopularItem
import com.bumptech.glide.Glide

class HomePopularAdapter(
    private var items: List<PopularItem> = listOf(),
    private var onItemClicked: OnCategoryClicked? = null,
) : RecyclerView.Adapter<HomePopularAdapter.HomePopularViewHolder>() {
    fun setOnDepartmentItemClicked(onItemClicked: OnCategoryClicked) {
        this.onItemClicked = onItemClicked
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(newItems: List<PopularItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomePopularViewHolder(
        CardPopularItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomePopularViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class HomePopularViewHolder(val binding: CardPopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked?.onClicked(items[bindingAdapterPosition], bindingAdapterPosition)
            }
        }

        fun bind(item: PopularItem) {
            binding.name.text = item.name
            binding.btnFavourite.setImageResource(
                if (item.isFavorite) {
                    R.drawable.ic_favourite_checked
                } else {
                    R.drawable.ic_favourite_unchecked
                }
            )

            binding.distance.text =
                if (item.distance != null) {
                    binding.root.context.getString(R.string.distance_km, item.distance)
                } else {
                    binding.root.context.getString(R.string.unknown)
                }

            Glide
                .with(binding.root.context)
                .load(item.image)
                .into(binding.popularImage)

            binding.favouriteNum.text = item.popular.toString()

            when (item.popular) {
                1 -> {
                    binding.star1.setImageResource(R.drawable.ic_filled_star)
                }

                2 -> {
                    binding.star1.setImageResource(R.drawable.ic_filled_star)
                    binding.star2.setImageResource(R.drawable.ic_filled_star)

                }

                3 -> {
                    binding.star1.setImageResource(R.drawable.ic_filled_star)
                    binding.star2.setImageResource(R.drawable.ic_filled_star)
                    binding.star3.setImageResource(R.drawable.ic_filled_star)
                }

                4 -> {
                    binding.star1.setImageResource(R.drawable.ic_filled_star)
                    binding.star2.setImageResource(R.drawable.ic_filled_star)
                    binding.star3.setImageResource(R.drawable.ic_filled_star)
                    binding.star4.setImageResource(R.drawable.ic_filled_star)
                }

                5 -> {
                    binding.star1.setImageResource(R.drawable.ic_filled_star)
                    binding.star2.setImageResource(R.drawable.ic_filled_star)
                    binding.star3.setImageResource(R.drawable.ic_filled_star)
                    binding.star4.setImageResource(R.drawable.ic_filled_star)
                    binding.star5.setImageResource(R.drawable.ic_filled_star)
                }
            }
        }
    }


    interface OnCategoryClicked {
        fun onClicked(
            item: PopularItem, selectedIndex: Int
        )
    }
}