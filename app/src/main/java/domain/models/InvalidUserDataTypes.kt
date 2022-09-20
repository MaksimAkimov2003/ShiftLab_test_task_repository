package domain.models

class InvalidUserDataTypes(var isNameValidateError: Boolean, var isSurnameValidateError: Boolean,
                           var isEmailValidateError: Boolean, var isPasswordValidateError: Boolean, var isPasswordRepeateValidateError: Boolean){
}