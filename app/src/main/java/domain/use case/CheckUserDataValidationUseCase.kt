package domain.use

class CheckUserDataValidationUseCase {

    private companion object {
        val NAME_AND_SURNAME_REGEX = Regex("""([A-Z]||[А-Я])(([a-z]+)||([а-я])+)""")
        val EMAIL_REGEX = Regex("""[A-zA]([A-Za-z0-9\._])+[@]([A-zA])+[.]((com)|(ru))""")
        val PASSWORD_REGEX = Regex("""^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*${'$'}""")
    }

    fun isValidAllData(): Boolean {
        TODO("Прописать этот метод")
    }

    fun checkNameValidation(name: String): Boolean {
        if (!name.matches(NAME_AND_SURNAME_REGEX)) return false

        return true
    }

    fun checkSurnameValidation(surname: String): Boolean {
        if (!surname.matches(NAME_AND_SURNAME_REGEX)) return false

        return true
    }

    fun checkEmailValidation(email: String): Boolean {
        if (!email.matches(EMAIL_REGEX)) return false

        return true
    }

    fun checkPasswordValidation(password: String): Boolean {
        if (!password.matches(PASSWORD_REGEX)) return false

        return true
    }
}