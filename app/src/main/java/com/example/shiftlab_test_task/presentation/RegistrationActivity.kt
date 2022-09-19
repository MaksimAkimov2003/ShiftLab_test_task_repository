package com.example.shiftlab_test_task.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shiftlab_test_task.R
import com.example.shiftlab_test_task.databinding.ActivityRegistrationBinding
import data.repository.UserRepositoryImpl.SharedPrefsInformation.KEY_USER_PASSWORD
import data.repository.UserRepositoryImpl.SharedPrefsInformation.SHARED_PREFS_USER
import domain.models.InvalidUserDataTypes
import domain.models.UserData

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModelRegistration: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefsUser = getSharedPreferences(SHARED_PREFS_USER, MODE_PRIVATE)


        sharedPrefsUser.edit().clear().apply() // убери это

        val savedPassword = sharedPrefsUser.getString(KEY_USER_PASSWORD, "")

        val intent = Intent(this, MainScreenActivity::class.java)

        if (savedPassword != "") {
            Log.e("CCC", savedPassword.toString())
            startActivity(intent)
        }

        else {
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

                val userData = UserData(
                    userName = userName,
                    userSurname = userSurname,
                    userEmail = userEmail,
                    userPassword = userPassword
                )

                val invalideUserDataTypes = viewModelRegistration.searchIncorrectData(userData = userData)

                if (invalideUserDataTypes == null) {
                    viewModelRegistration.save(userData)
                    startActivity(intent)
                }

                else {
                    showUsersErrors(invalideUserDataTypes)
                }

            }

            binding.buttonRestoreData.setOnClickListener {
                viewModelRegistration.load()
            }
        }

    }

    private fun showUsersErrors(invalidUserDataTypes: InvalidUserDataTypes) {
        if (invalidUserDataTypes.isNameValidateError) {
            Log.e("Debug", "name = incorrect")
            binding.nameTextInputLayout.error = getString(R.string.invalid_name_message)
        }

        else {
            binding.nameTextInputLayout.error = null
        }

        if (invalidUserDataTypes.isSurnameValidateError) {
            Log.e("Debug", "surname = incorrect")
            binding.surnameTextInputLayout.error = getString(R.string.invalid_surname_message)
        }

        else {
            binding.surnameTextInputLayout.error = null
        }

        if (invalidUserDataTypes.isEmailValidateError) {
            Log.e("Debug", "email = incorrect")
            binding.emailTextInputLayout.error = getString(R.string.invalid_email_message)
        }

        else {
            binding.emailTextInputLayout.error = null
        }

        if (invalidUserDataTypes.isPasswordValidateError) {
            Log.e("Debug", "password = incorrect")
            binding.passwordTextInputLayout.error = getString(R.string.invalid_password_message)
        }

        else {
            binding.passwordTextInputLayout.error = null
        }
    }

}