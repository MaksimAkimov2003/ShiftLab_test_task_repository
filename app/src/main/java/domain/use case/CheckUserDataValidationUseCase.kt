package domain.use

import domain.models.InvalidUserDataTypes
import domain.models.UserData

class CheckUserDataValidationUseCase {

    private companion object {
        val NAME_AND_SURNAME_REGEX = Regex("""([A-Z]||[А-Я])(([a-z]+)||([а-я])+)""")
        val EMAIL_REGEX = Regex("""[A-zA]([A-Za-z0-9\._])+[@]([A-zA])+[.]((com)|(ru))""")
        val PASSWORD_REGEX = Regex("""^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*${'$'}""")
    }

    fun validationAllData(userData: UserData): InvalidUserDataTypes {
        lateinit var invalidUserDataTypes: InvalidUserDataTypes

        val isNameValidateError = checkNameErrors(userData.userName)
        val isSurnameValidateError = checkSurnameErrors(userData.userSurname)
        val isEmailValidateError = checkEmailErrors(userData.userEmail)
        val isPasswordValidateError = checkPasswordErrors(userData.userPassword)
        val isPasswordRepeateValidateError = checkPasswordRepeateErrors(passwordRepeat = userData.userPasswordRepeat,
            password = userData.userPassword)

        invalidUserDataTypes = InvalidUserDataTypes(isNameValidateError = isNameValidateError, isSurnameValidateError = isSurnameValidateError,
        isEmailValidateError = isEmailValidateError, isPasswordValidateError = isPasswordValidateError, isPasswordRepeateValidateError = isPasswordRepeateValidateError)

        return invalidUserDataTypes
    }

    private fun checkNameErrors(name: String): Boolean {
        if (!name.matches(NAME_AND_SURNAME_REGEX)) return true

        return false
    }

    private fun checkSurnameErrors(surname: String): Boolean {
        if (!surname.matches(NAME_AND_SURNAME_REGEX)) return true

        return false
    }

    private fun checkEmailErrors(email: String): Boolean {
        if (!email.matches(EMAIL_REGEX)) return true

        return false
    }

    private fun checkPasswordErrors(password: String): Boolean {
        if (!password.matches(PASSWORD_REGEX)) return true

        return false
    }

    private fun checkPasswordRepeateErrors(passwordRepeat: String, password: String): Boolean {
        if (passwordRepeat != password) return true

        return false
    }
}