package com.example.shiftlab_test_task.presentation.RegistrationActivityPresentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shiftlab_test_task.R
import com.example.shiftlab_test_task.databinding.ActivityRegistrationBinding
import com.example.shiftlab_test_task.presentation.MainScreenActivityPresentation.MainScreenActivity
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


        val savedPassword = sharedPrefsUser.getString(KEY_USER_PASSWORD, "")

        val intent = Intent(this, MainScreenActivity::class.java)

        if (savedPassword != "") {
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
            binding.nameTextInputLayout.error = getString(R.string.invalid_name_message)
        }

        else {
            binding.nameTextInputLayout.error = null
        }

        if (invalidUserDataTypes.isSurnameValidateError) {
            binding.surnameTextInputLayout.error = getString(R.string.invalid_surname_message)
        }

        else {
            binding.surnameTextInputLayout.error = null
        }

        if (invalidUserDataTypes.isEmailValidateError) {
            binding.emailTextInputLayout.error = getString(R.string.invalid_email_message)
        }

        else {
            binding.emailTextInputLayout.error = null
        }

        if (invalidUserDataTypes.isPasswordValidateError) {
            binding.passwordTextInputLayout.error = getString(R.string.invalid_password_message)
        }

        else {
            binding.passwordTextInputLayout.error = null
        }
    }

}