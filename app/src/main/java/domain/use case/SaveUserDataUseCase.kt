package domain.use

import domain.models.UserData
import domain.repository.UserRepository

class SaveUserDataUseCase(private val userRepository: UserRepository) {

    fun execute(saveParametrs: UserData) {
        userRepository.saveUserData(saveParametrs = saveParametrs)
    }
}