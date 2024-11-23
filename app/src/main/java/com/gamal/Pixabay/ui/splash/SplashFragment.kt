package com.gamal.Pixabay.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.gamal.Pixabay.R
import com.gamal.Pixabay.databinding.FragmentSplashBinding
import com.gamal.Pixabay.utils.DataStoreUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private  var isUserLoggedIn: Boolean=false




        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)

      runBlocking {
          isUserLoggedIn=   DataStoreUtil(requireContext()).getCheckSessionUser()
      }
        return binding.root
    }



    override fun onResume() {
        super.onResume()
        binding.ivSplash.animate().setDuration(2000).alpha(1f).withEndAction {
            checkFirstTime()
        }
    }

    private  fun checkFirstTime() {


        if (isUserLoggedIn){
            NavHostFragment.findNavController(this@SplashFragment)
                .navigate(R.id.action_splashFragment_to_homeFragment)
        }else{
            NavHostFragment.findNavController(this@SplashFragment)
                .navigate(R.id.action_splashFragment_to_loginFragment)
        }

    }


}