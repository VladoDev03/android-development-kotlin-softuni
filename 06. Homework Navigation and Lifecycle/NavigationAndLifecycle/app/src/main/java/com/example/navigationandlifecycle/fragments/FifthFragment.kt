package com.example.navigationandlifecycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationandlifecycle.databinding.FragmentFifthBinding
import com.example.navigationandlifecycle.databinding.FragmentFourthBinding

class FifthFragment : Fragment() {
    lateinit var binding: FragmentFifthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFifthBinding.inflate(inflater, container, false)
        binding.textView.text = "This is the last (fifth) epic random fragment №10/2 with an unique title :)"

        binding.nextFragment.setOnClickListener {
            val action = FifthFragmentDirections.actionFifthFragmentToFirstFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}