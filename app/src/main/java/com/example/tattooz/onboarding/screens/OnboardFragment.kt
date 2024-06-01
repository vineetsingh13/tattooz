package com.example.tattooz.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

import com.example.tattooz.R
import com.example.tattooz.databinding.FragmentOnboardBinding


class OnboardFragment : Fragment() {

    private lateinit var binding: FragmentOnboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOnboardBinding.inflate(inflater,container,false)

        val viewPager=activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.next.setOnClickListener {
            viewPager?.currentItem=1
        }

        return binding.root
    }


}