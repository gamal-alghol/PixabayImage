package com.gamal.Pixabay.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.gamal.Pixabay.R
import com.gamal.Pixabay.data.model.User
import com.gamal.Pixabay.databinding.FragmentRegistarBinding
import com.gamal.Pixabay.utils.DataStoreUtil
import com.gamal.Pixabay.utils.areAllFieldsFilled
import com.gamal.Pixabay.utils.isAgeValid
import com.gamal.Pixabay.utils.isEmailValid
import com.gamal.Pixabay.utils.isPasswordValid
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegistarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistarBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
setupUi()
    }

    private   fun setupUi() {
        binding.tvLogIn.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigateUp()
        }


        binding.btCreateAccount.setOnClickListener {

            if (areAllFieldsFilled(
                    listOf(
                        binding.edName,
                        binding.edEmail,
                        binding.edAge,
                        binding.edPassword,
                        binding.edConfirmPassword
                    ), requireContext()
                )
            ) {


                if (!isEmailValid(binding.edEmail.text.toString())) {
                    binding.edEmail.error = getString(R.string.Please_enter_a_valid_email)
                    return@setOnClickListener
                }
                if (!isPasswordValid(
                        listOf(
                            binding.edPassword,
                            binding.edConfirmPassword
                        )
                    )

                ) {
                    binding.edPassword.error = getString(R.string.Password_must_be_6_12_characters)
                    binding.edConfirmPassword.error = getString(R.string.Password_must_be_6_12_characters)
                    return@setOnClickListener
                }
                if (!isAgeValid(
                        listOf(binding.edAge)
                    )

                ) {
                    binding.edAge.error = getString(R.string.age_must_be_18_99_year)

                    return@setOnClickListener
                }

                if (binding.edPassword.text.toString() != binding.edConfirmPassword.text.toString()) {
                    binding.edConfirmPassword.error = getString(R.string.Passwords_do_not_match)

                    return@setOnClickListener
                }
                registerUser()
            }
        }
    }

        @OptIn(DelicateCoroutinesApi::class)
        private fun registerUser() {
            val user = User(
                binding.edName.text.toString(),
                binding.edEmail.text.toString(),
                binding.edAge.text.toString().toInt(),
                binding.edPassword.text.toString()
            )
            val gson = Gson()
            val userJson = gson.toJson(user)
            GlobalScope.launch {
                DataStoreUtil(requireContext()).saveUser(userJson)
                DataStoreUtil.getInstance(requireContext()).setCheckSessionUser(true)

            }
            Thread.sleep(1000)
            NavHostFragment.findNavController(this@RegisterFragment)
                .navigate(R.id.action_registarFragment_to_homeFragment)

        }

    }


