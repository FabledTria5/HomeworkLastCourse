package com.fabledt5.drivingcar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabledt5.drivingcar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val configuration = resources.configuration
        val widthDp = configuration.screenWidthDp.toFloat()
        val heightDp = configuration.screenHeightDp.toFloat()

        val car = Car(binding.car, widthDp * 2, heightDp * 2)
        binding.car.setOnClickListener { car.drive() }
    }
}