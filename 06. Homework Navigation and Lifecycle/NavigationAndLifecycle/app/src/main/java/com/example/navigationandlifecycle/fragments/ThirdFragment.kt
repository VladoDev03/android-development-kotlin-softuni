package com.example.navigationandlifecycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationandlifecycle.databinding.FragmentSecondBinding
import com.example.navigationandlifecycle.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        binding.textView.text = "This is the third fragment №3"

        binding.nextFragment.setOnClickListener {
            val action = ThirdFragmentDirections.actionThirdFragmentToFourthFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}