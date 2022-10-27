package com.example.navigationandlifecycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationandlifecycle.databinding.FragmentFourthBinding
import com.example.navigationandlifecycle.databinding.FragmentThirdBinding

class FourthFragment : Fragment() {
    lateinit var binding: FragmentFourthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourthBinding.inflate(inflater, container, false)
        binding.textView.text = "This is the fourth epic random fragment №5-1 :)"

        binding.nextFragment.setOnClickListener {
            val action = FourthFragmentDirections.actionFourthFragmentToFifthFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}