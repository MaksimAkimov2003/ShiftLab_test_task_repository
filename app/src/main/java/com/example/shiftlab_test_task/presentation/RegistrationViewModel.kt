package com.example.shiftlab_test_task.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.models.UserData
import domain.use.GetUserDataUseCase
import domain.use.SaveUserDataUseCase

class RegistrationViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase
): ViewModel() {

    val liveData = MutableLiveData<UserData>()

    init {
        Log.e("life_cycle", "VM created")
    }

    override fun onCleared() {
        Log.e("life_cycle", "VM cleared")
        super.onCleared()
    }

    fun save(userData: UserData) {
        saveUserDataUseCase.execute(saveParametrs = userData)
    }

    fun load() {
        val userData: UserData = getUserDataUseCase.execute()
        liveData.value = userData
    }
}