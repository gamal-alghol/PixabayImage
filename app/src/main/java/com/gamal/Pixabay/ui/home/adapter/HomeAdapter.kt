package com.gamal.Pixabay.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.gamal.Pixabay.R
import com.gamal.Pixabay.data.model.Hit
import com.gamal.Pixabay.databinding.ItemImageBinding

class HomeAdapter(private val context: android.content.Context, private var listener: (Hit) -> Unit) :
    PagingDataAdapter<Hit, HomeAdapter.ImageViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemBinding =
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ImageViewHolder(
            itemBinding
        )
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(image = getItem(position)!!)

    }



    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Hit) {

            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.gray200))
            circularProgressDrawable.start()

            Glide.with(binding.root)
                .load(image.webformatURL)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(binding.ivImage)
            binding.tvUploader.text = image.user

            binding.root.setOnClickListener {
                listener(image)

            }
        }


    }



}