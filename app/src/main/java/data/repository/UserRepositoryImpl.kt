package data.repository

import android.content.Context
import com.example.shiftlab_test_task.R
import domain.models.UserData
import domain.repository.UserRepository

class UserRepositoryImpl(context: Context): UserRepository {
    companion object SharedPrefsInformation {
        const val SHARED_PREFS_USER = R.string.SHARED_PREFS_USER.toString()
        const val KEY_USER_NAME = R.string.KEY_USER_NAME.toString()
        const val KEY_USER_SURNAME = R.string.KEY_USER_SURNAME.toString()
        const val KEY_USER_EMAIL = R.string.KEY_USER_EMAIL.toString()
        const val KEY_USER_PASSWORD = R.string.KEY_USER_PASSWORD.toString()
    }

    private val sharedPrefsUser = context.getSharedPreferences(SHARED_PREFS_USER, Context.MODE_PRIVATE)

    override fun saveUserData(saveParametrs: UserData): Boolean {
        sharedPrefsUser.edit().putString(KEY_USER_NAME, saveParametrs.userName).apply()
        sharedPrefsUser.edit().putString(KEY_USER_SURNAME, saveParametrs.userSurname).apply()
        sharedPrefsUser.edit().putString(KEY_USER_EMAIL, saveParametrs.userEmail).apply()
        sharedPrefsUser.edit().putString(KEY_USER_PASSWORD, saveParametrs.userPassword).apply()

        return true
    }

    override fun getUserData(): UserData {
        val userName = sharedPrefsUser.getString(KEY_USER_NAME, "") ?:""
        val userSurname = sharedPrefsUser.getString(KEY_USER_SURNAME, "") ?:""
        val userEmail = sharedPrefsUser.getString(KEY_USER_EMAIL, "") ?:""
        val userPassword = sharedPrefsUser.getString(KEY_USER_PASSWORD, "") ?:""

        return UserData(userName = userName, userSurname = userSurname, userEmail = userEmail, userPassword = userPassword)
    }
}