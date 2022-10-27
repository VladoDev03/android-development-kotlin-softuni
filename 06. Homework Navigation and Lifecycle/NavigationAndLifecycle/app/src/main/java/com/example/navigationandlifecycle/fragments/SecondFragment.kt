package com.example.navigationandlifecycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationandlifecycle.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    lateinit var binding: FragmentSecondBinding
    private val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.param.text = "param: ${args.firstParam}"

        binding.nextFragment.setOnClickListener {
            val action = SecondFragmentDirections.actionSecondFragmentToThirdFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }
}