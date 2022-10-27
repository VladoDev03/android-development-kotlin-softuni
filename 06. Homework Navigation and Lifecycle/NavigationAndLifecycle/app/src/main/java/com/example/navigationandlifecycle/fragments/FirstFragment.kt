package com.example.navigationandlifecycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.navigationandlifecycle.R
import com.example.navigationandlifecycle.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.nextFragment.setOnClickListener {
//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.container, SecondFragment())
//            transaction.addToBackStack("second_transaction")
//            transaction.commit()

            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment("some value")
            findNavController().navigate(action)
        }

        return binding.root
    }
}