package com.example.shiftlab_test_task.presentation.MainScreenActivityPresentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.repository.UserRepositoryImpl
import domain.use.GetUserDataUseCase

class MainScreenViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val userRepository by lazy(LazyThreadSafetyMode.NONE) {
        UserRepositoryImpl(context = context)
    }

    private val getUserDataUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetUserDataUseCase(userRepository = userRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(getUserDataUseCase = getUserDataUseCase) as T
    }
}