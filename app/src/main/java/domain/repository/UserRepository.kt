package domain.repository

import domain.models.UserData

interface UserRepository {
    fun saveUserData(saveParametrs: UserData): Boolean

    fun getUserData(): UserData
}