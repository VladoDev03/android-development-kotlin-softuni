package com.example.navigationandlifecycle

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.navigationandlifecycle.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titleText: TextView = binding.titleText
        val backButton: Button = binding.backButton

        titleText.text = intent.extras?.getString("title")

        backButton.setOnClickListener {
            this.finish()
        }
    }
}