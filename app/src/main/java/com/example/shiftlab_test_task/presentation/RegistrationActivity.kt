package com.example.shiftlab_test_task.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shiftlab_test_task.R
import com.example.shiftlab_test_task.databinding.ActivityRegistrationBinding
import com.google.android.material.textfield.TextInputEditText
import domain.models.UserData

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModelRegistration: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelRegistration = ViewModelProvider(this, RegistrationViewModelFactory(this))
            .get(RegistrationViewModel::class.java)

        viewModelRegistration.liveData.observe(this, Observer {
            binding.name.setText(it.userName)
            binding.surname.setText(it.userSurname)
            binding.email.setText(it.userEmail)
            binding.password.setText(it.userPassword)
        })

        binding.buttonInviteToRegistration.setOnClickListener {
            val userName = binding.name.text.toString()
            val userSurname = binding.surname.text.toString()
            val userEmail = binding.email.text.toString()
            val userPassword = binding.password.text.toString()

            val savingUserData = UserData(userName = userName, userSurname = userSurname, userEmail = userEmail, userPassword = userPassword)

            viewModelRegistration.save(savingUserData)
        }

        binding.buttonRestoreData.setOnClickListener {
            viewModelRegistration.load()
        }
    }
}