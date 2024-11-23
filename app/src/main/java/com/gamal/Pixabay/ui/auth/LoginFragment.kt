package com.gamal.Pixabay.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.gamal.Pixabay.R
import com.gamal.Pixabay.data.model.User
import com.gamal.Pixabay.databinding.FragmentLoginBinding
import com.gamal.Pixabay.utils.DataStoreUtil
import com.gamal.Pixabay.utils.areAllFieldsFilled
import com.gamal.Pixabay.utils.isEmailValid
import com.gamal.Pixabay.utils.isPasswordValid
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private  var user: User? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        GlobalScope.launch {
             DataStoreUtil(requireContext()).getUser().also { it->
                if (it != null) {
                    user=it
                }
            }
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {


        binding.tvSignUp.setOnClickListener {
            NavHostFragment.findNavController(this@LoginFragment)
                .navigate(R.id.action_loginFragment_to_registarFragment)
        }
        binding.login.setOnClickListener {

            if (areAllFieldsFilled(
                    listOf(
                        binding.edEmail,
                        binding.edPassword,
                    ), requireContext()
                )
            ) {


                if (!isEmailValid(binding.edEmail.text.toString())) {
                    binding.edEmail.error = getString(R.string.Please_enter_a_valid_email)

                    return@setOnClickListener
                }
                if (!isPasswordValid(listOf(binding.edPassword))) {
                    binding.edPassword.error = getString(R.string.Password_must_be_6_12_characters)


                    return@setOnClickListener
                }

                runBlocking{
                 loginUser()

                }


            }
        }
    }

    private suspend fun loginUser() {
        if (user != null) {
        if (user!!.email == binding.edEmail.text.toString() && user!!.password == binding.edPassword.text.toString()) {
            DataStoreUtil.getInstance(requireContext()).setCheckSessionUser(true)
            NavHostFragment.findNavController(this@LoginFragment)
                .navigate(R.id.action_loginFragment_to_homeFragment)
        }else{
                Toast.makeText(requireContext(), getString(R.string.Invalid_email_or_password), Toast.LENGTH_SHORT).show()

        }
    }else{
            Toast.makeText(requireContext(), getString(R.string.user_not_found), Toast.LENGTH_SHORT).show()

    }
    }


}