package com.gamal.Pixabay.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.gamal.Pixabay.R
import com.gamal.Pixabay.data.model.User
import com.gamal.Pixabay.databinding.FragmentHomeBinding
import com.gamal.Pixabay.ui.home.adapter.HomeAdapter
import com.gamal.Pixabay.data.viewModel.HomeViewModel
import com.gamal.Pixabay.utils.BASE_URL
import com.gamal.Pixabay.utils.DataStoreUtil
import com.gamal.Pixabay.utils.HIT_DATA
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var user: User? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private  var   homeAdapter: HomeAdapter?=null
    private  var page: Int=1
    private  var perPage: Int=15
    private  var totalImage: Int=500

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment, it should only be done once
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareAdapter()

        lifecycleScope.launch {
            user = DataStoreUtil(requireContext()).getUser()

            user?.let {
                binding.toolbar.name.text = it.name
            }
            setupUI()
        }
    }

    private fun setupUI() {
        fetchData()
        logout()
    }

    private fun logout() {
        binding.toolbar.logout.setOnClickListener {
            lifecycleScope.launch {
                DataStoreUtil.getInstance(requireContext()).setCheckSessionUser(false)
                NavHostFragment.findNavController(this@HomeFragment)
                    .navigate(R.id.action_homeFragment_to_splashFragment)
            }
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.getPagedImages("flowers").collectLatest { pagingData ->

                homeAdapter!!.submitData(pagingData)

                Log.d("result", "fetchData: $page")

            }


        }

lifecycleScope.launch {
    homeAdapter!!.loadStateFlow.collectLatest { loadStates ->
        Log.d("result", "homeAdapter: ${homeAdapter!!.itemCount}")
        when {
            (loadStates.refresh is LoadState.Loading && homeAdapter!!.itemCount == 0) -> {
                // Initial load is happening
                binding.loading.isVisible = true
                binding.parentLay.isVisible = false
            }
            (loadStates.refresh is LoadState.Loading && homeAdapter!!.itemCount > 0) -> {
                // Initial load is happening
                binding.loadingPagnation.isVisible = true
            }
            loadStates.refresh is LoadState.Error -> {
                // Error occurred during initial load
                binding.rvImages.isVisible = false
                binding.parentLay.isVisible = false

                Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
            }
            loadStates.refresh is LoadState.NotLoading -> {
                // Data successfully loaded
                binding.loading.isVisible = false
                binding.loadingPagnation.isVisible = false
                binding.parentLay.isVisible = true


            }
        }


    }
}
    }





    private fun prepareAdapter() {
        binding.rvImages.layoutManager = GridLayoutManager(requireActivity(),2)
         homeAdapter=HomeAdapter(requireContext()) {
            hit ->
             val gson = Gson()
             val hitJson = gson.toJson(hit)
             val bundle=Bundle()
             bundle.putString(HIT_DATA,hitJson)
             NavHostFragment.findNavController(this@HomeFragment)
                 .navigate(R.id.action_homeFragment_to_imageDetailsFragment,bundle)

        }
       //
        binding.rvImages.adapter =homeAdapter
       // binding.rvImages.hasFixedSize()
    }
}
