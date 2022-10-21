package com.example.navigationandlifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.navigationandlifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nextButton: Button = binding.nextButton
        val inputText: EditText = binding.inputText

        nextButton.setOnClickListener {
            val input: String = inputText.text.toString()
            inputText.setText("")

            val argsBundle: Bundle = Bundle()
            argsBundle.putString("title", input)

            val intent: Intent = Intent(this, SecondActivity::class.java)
            intent.putExtras(argsBundle)

            startActivity(intent)
        }
    }
}