package com.example.opsc6312demo1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.opsc6312demo1.databinding.MainActivity2Binding

class MainActivity2  : AppCompatActivity() {

    private lateinit var binding: MainActivity2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the PostFragment into the container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostFragment2())
            .commit()
    }
}