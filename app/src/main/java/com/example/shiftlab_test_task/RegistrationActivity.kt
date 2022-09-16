package com.example.shiftlab_test_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shiftlab_test_task.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}