package com.bb4first.mycashtask.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bb4first.mycashtask.databinding.CardCategoryItemBinding
import com.bb4first.mycashtask.enums.AppLanguage
import com.bb4first.mycashtask.model.home.homeBaseCategoriesResponse.Category
import com.bumptech.glide.Glide

class HomeCategoriesAdapter(
    private val appLanguage: AppLanguage,
    private var items: List<Category> = listOf(),
    private var onItemClicked: OnCategoryClicked? = null,
) : RecyclerView.Adapter<HomeCategoriesAdapter.HomeCategoriesViewHolder>() {
    fun setOnDepartmentItemClicked(onItemClicked: OnCategoryClicked) {
        this.onItemClicked = onItemClicked
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(newItems: List<Category>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeCategoriesViewHolder(
        CardCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeCategoriesViewHolder, position: Int) {
        holder.bind(items[position])
    }



    inner class HomeCategoriesViewHolder(val binding: CardCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked?.onClicked(items[bindingAdapterPosition], bindingAdapterPosition)
            }
        }

        fun bind(item: Category) {
            // note: if the name is coming for the backend localized
            // binding.nameCategory.text = item.name

            binding.nameCategory.text = if (appLanguage == AppLanguage.ARABIC) {
                item.nameAr
            } else {
                item.nameEn
            }

            Glide.with(binding.root.context).load(item.image).into(binding.imgCategory)
        }
    }


    interface OnCategoryClicked {
        fun onClicked(
            item: Category, selectedIndex: Int
        )
    }
}