package com.gamal.Pixabay.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

import com.gamal.Pixabay.R
import com.gamal.Pixabay.data.model.Hit
import com.gamal.Pixabay.data.model.User
import com.gamal.Pixabay.databinding.FragmentImageDetailsBinding
import com.gamal.Pixabay.databinding.FragmentLoginBinding
import com.gamal.Pixabay.utils.HIT_DATA
import com.google.android.material.chip.Chip
import com.google.gson.Gson


class ImageDetailsFragment : Fragment() {
    private lateinit var binding: FragmentImageDetailsBinding
    private var image: Hit? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val data = requireArguments().getString(HIT_DATA)
        image=  Gson().fromJson(data, Hit::class.java)

   return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()


    }

    private fun setupUI() {

        binding.ivArrowDown.setOnClickListener {
            NavHostFragment.findNavController(this@ImageDetailsFragment).popBackStack()

        }
        //section1
        setImage()
        binding.tvType.text=image!!.type.toString()
        binding.tvSize.text="${image!!.imageSize/1024}MP"
        tagChips(tags = image!!.tags)
        //section2
        binding.uploader.text=image!!.user
        binding.tvView.text="${formatNumberToK(image!!.views)} Views"
        binding.tvLike.text="${formatNumberToK(image!!.likes)} Likes"
        binding.tvComm.text="${formatNumberToK(image!!.comments)} Comments"
        binding.tvSave.text="${formatNumberToK(image!!.collections)} Favorites"
        binding.tvDownload.text="${formatNumberToK(image!!.downloads)} Downloads"


    }

    private fun tagChips(tags: String) {
        val tagList = tags.split(",").map { it.trim() }
        for (tag in tagList) {
            val chip = Chip(requireContext()).apply {
                text = tag
                isCheckable=false
                chipStrokeWidth=0f
                chipBackgroundColor = resources.getColorStateList(R.color.secondary_color, null)
                setTextColor(resources.getColor(R.color.gray100, null))


            }
            binding.chipGroup.addView(chip)
        }
    }


    private fun setImage() {
        val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.gray200))
        circularProgressDrawable.start()

        Glide.with(binding.root)
            .load(image!!.webformatURL)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(binding.ivImage)
    }
    fun formatNumberToK(value: Int): String {
        return if (value >= 1000) {
            val formatted = value / 1000 // Convert to 'k' scale
            "${formatted}k+"
        } else {
            value.toString() // Return the original number if less than 1000
        }
    }
}