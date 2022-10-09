package com.example.androidbasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.androidbasics.databinding.ActivityMainBinding
import java.util.Stack

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var input: EditText
    private lateinit var view: TextView
    private lateinit var submit: Button
    private lateinit var eraser: Button
    private lateinit var undo: Button

    private var changeCounter: Int = 0

    private val allEntries: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        input = binding.editText
        view = binding.showText
        submit = binding.submitButton
        eraser = binding.eraser
        undo = binding.undoButton

        allEntries.add("")

        submit.setOnClickListener {
            val newTextValue: String = input.text.toString()

            changeCounter++
            allEntries.add(newTextValue)

            view.text = "$changeCounter. $newTextValue"
            input.setText("")
        }

        eraser.setOnClickListener {
            changeCounter = 0

            view.text = ""
            input.setText("")

            allEntries.clear()
        }

        undo.setOnClickListener {
            changeCounter--

            if (changeCounter <= 0) {
                view.text = ""
                changeCounter = 0
            } else {
                val lastValue: String = allEntries[allEntries.size - 2]
                allEntries.removeLast()
                view.text = "$changeCounter. $lastValue"
            }

            input.setText("")
        }
    }
}