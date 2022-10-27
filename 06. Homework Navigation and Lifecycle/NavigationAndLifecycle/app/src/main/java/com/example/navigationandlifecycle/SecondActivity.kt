package com.example.navigationandlifecycle

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.example.navigationandlifecycle.databinding.ActivitySecondBinding
import com.example.navigationandlifecycle.fragments.FifthFragmentDirections
import com.example.navigationandlifecycle.fragments.FirstFragmentDirections

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container, FirstFragment())
//        transaction.addToBackStack("first_transaction")
//        transaction.commit()

//        val action = FirstFragmentDirections.actionFirstFragment()
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
//        navHostFragment.navController.navigate(action)

        val titleText: TextView = binding.titleText
        val backButton: Button = binding.backButton

        val title: String = intent.extras?.getString("title").toString()
        titleText.text = "Second activity title is $title"

        backButton.setOnClickListener {
            this.finish()
        }
    }
}