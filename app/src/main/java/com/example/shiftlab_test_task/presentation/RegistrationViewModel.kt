package com.example.shiftlab_test_task.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.models.UserData
import domain.use.CheckUserDataValidationUseCase
import domain.use.GetUserDataUseCase
import domain.use.SaveUserDataUseCase

class RegistrationViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val checkUserDataValidationUseCase: CheckUserDataValidationUseCase
): ViewModel() {

    val liveData = MutableLiveData<UserData>()

    fun save(userData: UserData) {
        saveUserDataUseCase.execute(saveParametrs = userData)
    }

    fun load() {
        val userData: UserData = getUserDataUseCase.execute()
        liveData.value = userData
    }

    fun checkName(name: String): Boolean {
        return checkUserDataValidationUseCase.checkNameValidation(name = name)
    }

    fun checkSurname(surname: String): Boolean {
        return checkUserDataValidationUseCase.checkSurnameValidation(surname = surname)
    }

    fun checkEmail(email: String): Boolean {
        return checkUserDataValidationUseCase.checkEmailValidation(email = email)
    }

    fun checkPassword(password: String): Boolean {
        return checkUserDataValidationUseCase.checkPasswordValidation(password = password)
    }
}