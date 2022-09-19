package com.example.shiftlab_test_task.presentation.MainScreenActivityPresentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.models.UserData
import domain.use.GetUserDataUseCase

class MainScreenViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
): ViewModel() {

    val liveData = MutableLiveData<UserData>()

    fun load() {
        val userData: UserData = getUserDataUseCase.execute()
        liveData.value = userData
    }
}