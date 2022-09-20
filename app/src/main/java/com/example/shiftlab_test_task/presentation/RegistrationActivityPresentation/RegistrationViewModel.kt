package com.example.shiftlab_test_task.presentation.RegistrationActivityPresentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.models.InvalidUserDataTypes
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

    fun searchIncorrectData(userData: UserData): InvalidUserDataTypes? {
        val invalidUserDataTypes = checkUserDataValidationUseCase.validationAllData(userData = userData)

        return if (!invalidUserDataTypes.isNameValidateError && !invalidUserDataTypes.isSurnameValidateError
            && !invalidUserDataTypes.isEmailValidateError && !invalidUserDataTypes.isPasswordValidateError)
            null
        else {
            invalidUserDataTypes
        }
    }
}