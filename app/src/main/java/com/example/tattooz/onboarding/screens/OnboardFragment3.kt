package com.example.tattooz.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tattooz.MainActivity

import com.example.tattooz.R
import com.example.tattooz.databinding.FragmentOnboard3Binding
import com.example.tattooz.onboarding.OnboardActivity
import com.example.tattooz.registration.LoginActivity


class OnboardFragment3 : Fragment() {

    private lateinit var binding: FragmentOnboard3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOnboard3Binding.inflate(inflater,container,false)

        val viewPager=activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.next.setOnClickListener {
            onBoardingFinished()
            val intent= Intent(requireActivity(), LoginActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}