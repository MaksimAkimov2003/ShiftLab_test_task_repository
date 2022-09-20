package com.example.shiftlab_test_task.presentation.RegistrationActivityPresentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shiftlab_test_task.R
import com.example.shiftlab_test_task.databinding.ActivityRegistrationBinding
import com.example.shiftlab_test_task.databinding.BottomSheetDataPickerBinding
import com.example.shiftlab_test_task.presentation.MainScreenActivityPresentation.MainScreenActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import data.repository.UserRepositoryImpl.SharedPrefsInformation.KEY_USER_PASSWORD
import data.repository.UserRepositoryImpl.SharedPrefsInformation.SHARED_PREFS_RESTORE
import data.repository.UserRepositoryImpl.SharedPrefsInformation.SHARED_PREFS_USER
import domain.models.InvalidUserDataTypes
import domain.models.UserData
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var bottomSheetBinding: BottomSheetDataPickerBinding
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

            binding.dataBorth.setOnClickListener {
                showBottomSheetForDataChoose()
            }

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
                val sharedPrefsRestore = getSharedPreferences(SHARED_PREFS_RESTORE, MODE_PRIVATE)
                val restoringPassword = sharedPrefsRestore.getString(KEY_USER_PASSWORD, "")

                if (restoringPassword != "") {
                    viewModelRegistration.load()
                }

                else {
                    Toast.makeText(
                        this,
                        R.string.no_saved_data,
                        Toast.LENGTH_SHORT
                    ).show()
                }
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

    @SuppressLint("SetTextI18n")
    private fun showBottomSheetForDataChoose() {
        bottomSheetBinding = BottomSheetDataPickerBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.day.minValue = 1
        bottomSheetBinding.day.maxValue = 31

        bottomSheetBinding.year.minValue = 1980
        bottomSheetBinding.year.maxValue = 2010

        val mouthes: Array<String> = arrayOf("Янв", "Фев", "Март", "Апр", "Май", "Июнь", "Июль", "Авг", "Сент", "Окт", "Ноя", "Дек")

        bottomSheetBinding.mounth.displayedValues = mouthes
        bottomSheetBinding.mounth.minValue = 0
        bottomSheetBinding.mounth.maxValue = mouthes.size - 1

        bottomSheetDialog.show()

        bottomSheetBinding.buttonCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.buttonOK.setOnClickListener {
            val dayString = convertDay(bottomSheetBinding.day.value)
            val mounthString = convertMounth(bottomSheetBinding.mounth.value)
            val yearString = bottomSheetBinding.year.value.toString()


            binding.dataBorth.text = "$dayString.$mounthString.$yearString"

            bottomSheetDialog.dismiss()
        }
    }

    private fun convertDay(day: Int): String {
        return if (day < 10) {
            "0$day"
        } else {
            day.toString()
        }
    }

    private fun convertMounth(mounth: Int): String {
        var mothString = "01"

        when(mounth) {
            0 -> mothString = "01"
            1 -> mothString = "02"
            2-> mothString = "03"
            3->  mothString = "04"
            4->  mothString = "05"
            5-> mothString = "06"
            6-> mothString = "07"
            7->  mothString = "08"
            8-> mothString = "09"
            9->  mothString = "10"
            10->  mothString = "11"
            11->  mothString = "12"
        }

        return mothString
    }
}