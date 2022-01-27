package com.fabledt5.ridingcar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabledt5.ridingcar.databinding.ActivityMainBinding
import android.util.DisplayMetrics

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        val car = Car(car = binding.car, screenWidth = dpWidth * 2, screenHeight = dpHeight * 2)
        binding.car.setOnClickListener { car.drive() }
    }
}