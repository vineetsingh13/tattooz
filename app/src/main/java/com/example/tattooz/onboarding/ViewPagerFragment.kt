package com.example.tattooz.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tattooz.R
import com.example.tattooz.databinding.FragmentViewPagerBinding
import com.example.tattooz.onboarding.screens.OnboardFragment
import com.example.tattooz.onboarding.screens.OnboardFragment2
import com.example.tattooz.onboarding.screens.OnboardFragment3


class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentViewPagerBinding.inflate(inflater,container,false)

        val fragmentList= arrayListOf<Fragment>(
            OnboardFragment(),
            OnboardFragment2(),
            OnboardFragment3()
        )

        val adapter=ViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)

        binding.viewPager.adapter=adapter
        return binding.root
    }


}