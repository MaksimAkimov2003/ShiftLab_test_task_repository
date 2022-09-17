package domain.use

import domain.models.UserData
import domain.repository.UserRepository

class GetUserDataUseCase(private val userRepository: UserRepository) {

    fun execute(): UserData {
        return userRepository.getUserData()
    }
}