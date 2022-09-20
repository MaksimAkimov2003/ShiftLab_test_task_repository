package com.example.shiftlab_test_task.presentation.MainScreenActivityPresentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shiftlab_test_task.databinding.ActivityMainScreenBinding
import com.example.shiftlab_test_task.presentation.RegistrationActivityPresentation.RegistrationActivity
import data.repository.UserRepositoryImpl

class MainScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var viewModelMainScreen: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builderAlertDialog = AlertDialog.Builder(this)

        viewModelMainScreen = ViewModelProvider(this, MainScreenViewModelFactory(this))
            .get(MainScreenViewModel::class.java)

        viewModelMainScreen.liveData.observe(this, Observer {
            val alertTittle = "Здравствуйте, " + it.userName + "\n" + "Данные вашего аккаунта:"
            val alertMessage = "Пароль: " + it.userPassword + "\n" + "Email: " + it.userEmail

            builderAlertDialog.setTitle(alertTittle)
            builderAlertDialog.setMessage(alertMessage)
        })

        viewModelMainScreen.load()

        binding.buttonDemonstrateUserData.setOnClickListener {
            val alertDialog: AlertDialog = builderAlertDialog.create()
            alertDialog.show()
            alertDialog.window?.setGravity(Gravity.CENTER)
        }

        binding.buttonBack.setOnClickListener {
            val sharedPrefsUser = getSharedPreferences(UserRepositoryImpl.SHARED_PREFS_USER, MODE_PRIVATE)
            sharedPrefsUser.edit().clear().apply()

            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}