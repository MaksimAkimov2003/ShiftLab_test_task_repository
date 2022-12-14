package com.example.shiftlab_test_task.presentation.RegistrationActivityPresentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.repository.UserRepositoryImpl
import domain.use.CheckUserDataValidationUseCase
import domain.use.GetUserDataUseCase
import domain.use.SaveUserDataUseCase

class RegistrationViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val userRepository by lazy(LazyThreadSafetyMode.NONE) {
        UserRepositoryImpl(context = context)
    }

    private val getUserDataUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetUserDataUseCase(userRepository = userRepository)
    }

    private val saveUserDataUseCase by lazy(LazyThreadSafetyMode.NONE) {
        SaveUserDataUseCase(userRepository = userRepository)
    }

    private val checkUserDataValidationUseCase by lazy(LazyThreadSafetyMode.NONE) {
        CheckUserDataValidationUseCase()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistrationViewModel(getUserDataUseCase = getUserDataUseCase, saveUserDataUseCase = saveUserDataUseCase,
            checkUserDataValidationUseCase = checkUserDataValidationUseCase) as T
    }
}