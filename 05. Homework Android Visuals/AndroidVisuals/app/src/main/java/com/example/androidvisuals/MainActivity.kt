package com.example.androidvisuals

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.androidvisuals.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var button: Button
    lateinit var image: ImageView

    var clicksCount: Int = 0

    val images: Array<Int> = arrayOf(
        R.drawable.baseline,
        R.drawable.battery,
        R.drawable.brush,
        R.drawable.clock,
        R.drawable.fingerprint,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button = binding.changeButton
        image = binding.image

        binding.clickCount = "0"

        button.setOnClickListener {
            clicksCount++
            binding.clickCount = clicksCount.toString()
            image.setImageResource(images[Random().nextInt(images.size)])
        }
    }
}