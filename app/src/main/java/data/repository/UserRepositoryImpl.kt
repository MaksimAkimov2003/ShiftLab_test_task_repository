package data.repository

import android.content.Context
import domain.models.UserData
import domain.repository.UserRepository

class UserRepositoryImpl(context: Context): UserRepository {
    private companion object SharedPrefsInformation {
        private val SHARED_PREFS_NAME = "shared_prefs_user_data"
        private val KEY_USER_NAME = "user_name"
        private val KEY_USER_SURNAME = "user_surname"
        private val KEY_USER_EMAIL = "user_email"
        private val KEY_USER_PASSWORD = "user_password"
    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveUserData(saveParametrs: UserData): Boolean {
        sharedPreferences.edit().putString(KEY_USER_NAME, saveParametrs.userName).apply()
        sharedPreferences.edit().putString(KEY_USER_SURNAME, saveParametrs.userSurname).apply()
        sharedPreferences.edit().putString(KEY_USER_EMAIL, saveParametrs.userEmail).apply()
        sharedPreferences.edit().putString(KEY_USER_PASSWORD, saveParametrs.userPassword).apply()

        return true
    }

    override fun getUserData(): UserData {
        val userName = sharedPreferences.getString(KEY_USER_NAME, "") ?:""
        val userSurname = sharedPreferences.getString(KEY_USER_SURNAME, "") ?:""
        val userEmail = sharedPreferences.getString(KEY_USER_EMAIL, "") ?:""
        val userPassword = sharedPreferences.getString(KEY_USER_PASSWORD, "") ?:""

        return UserData(userName = userName, userSurname = userSurname, userEmail = userEmail, userPassword = userPassword)
    }
}