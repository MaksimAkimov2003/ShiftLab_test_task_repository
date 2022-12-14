package data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.shiftlab_test_task.R
import data.repository.UserRepositoryImpl.SharedPrefsInformation.KEY_USER_PASSWORD_REPEAT
import domain.models.UserData
import domain.repository.UserRepository

class UserRepositoryImpl(context: Context): UserRepository {
    companion object SharedPrefsInformation {
        const val SHARED_PREFS_RESTORE = R.string.SHARED_PREFS_RESTORE.toString()
        const val SHARED_PREFS_USER = R.string.SHARED_PREFS_USER.toString()
        const val KEY_USER_NAME = R.string.KEY_USER_NAME.toString()
        const val KEY_USER_SURNAME = R.string.KEY_USER_SURNAME.toString()
        const val KEY_USER_EMAIL = R.string.KEY_USER_EMAIL.toString()
        const val KEY_USER_PASSWORD = R.string.KEY_USER_PASSWORD.toString()
        const val KEY_USER_PASSWORD_REPEAT = R.string.KEY_USER_PASSWORD_REPEAT.toString()
    }

    private val sharedPrefsUser = context.getSharedPreferences(SHARED_PREFS_USER, Context.MODE_PRIVATE)
    private val sharedPrefsRestore = context.getSharedPreferences(SHARED_PREFS_RESTORE, Context.MODE_PRIVATE)

    override fun saveUserData(saveParametrs: UserData): Boolean {
        saveDataInSharedPrefs(sharedPreferences = sharedPrefsUser, saveParametrs =  saveParametrs)
        saveDataInSharedPrefs(sharedPreferences =  sharedPrefsRestore, saveParametrs = saveParametrs)

        return true
    }

    override fun getUserData(): UserData {
        val userName = sharedPrefsRestore.getString(KEY_USER_NAME, "") ?:""
        val userSurname = sharedPrefsRestore.getString(KEY_USER_SURNAME, "") ?:""
        val userEmail = sharedPrefsRestore.getString(KEY_USER_EMAIL, "") ?:""
        val userPassword = sharedPrefsRestore.getString(KEY_USER_PASSWORD, "") ?:""
        val userPasswordRepeat = sharedPrefsRestore.getString(KEY_USER_PASSWORD_REPEAT, "") ?:""

        return UserData(userName = userName, userSurname = userSurname, userEmail = userEmail, userPassword = userPassword, userPasswordRepeat = userPasswordRepeat)
    }

    private fun saveDataInSharedPrefs(sharedPreferences: SharedPreferences, saveParametrs: UserData) {
        sharedPreferences.edit().putString(KEY_USER_NAME, saveParametrs.userName).apply()
        sharedPreferences.edit().putString(KEY_USER_SURNAME, saveParametrs.userSurname).apply()
        sharedPreferences.edit().putString(KEY_USER_EMAIL, saveParametrs.userEmail).apply()
        sharedPreferences.edit().putString(KEY_USER_PASSWORD, saveParametrs.userPassword).apply()
        sharedPreferences.edit().putString(KEY_USER_PASSWORD_REPEAT, saveParametrs.userPasswordRepeat).apply()
    }
}