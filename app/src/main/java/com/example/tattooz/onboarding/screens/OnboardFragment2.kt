package com.example.tattooz.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.tattooz.R
import com.example.tattooz.databinding.FragmentOnboard2Binding
import com.example.tattooz.databinding.FragmentOnboardBinding


class OnboardFragment2 : Fragment() {

    private lateinit var binding: FragmentOnboard2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentOnboard2Binding.inflate(inflater,container,false)

        val viewPager=activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.next.setOnClickListener {
            //here 2 is for the index which refers to the third screen
            viewPager?.currentItem=2
        }

        return binding.root
    }


}