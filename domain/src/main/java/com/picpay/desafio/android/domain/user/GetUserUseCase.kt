package com.picpay.desafio.android.domain.user

import com.picpay.desafio.android.domain.AbstractUseCase
import com.picpay.desafio.android.model.users.res.User
import com.picpay.desafio.android.repository.user.PicPayUserRepository

class GetUserUseCase constructor(
    private val picPayUserRepository: PicPayUserRepository
) : AbstractUseCase<Unit, List<User>>() {
    override suspend fun execute(param: Unit): List<User> =
        picPayUserRepository.getListOfUsers().map { user ->
            user.copy(
                img = if (user.img?.trim()?.isNotEmpty() == true)
                    user.img
                else null
            )
        }.filter { user ->
            user.name != null &&
                user.id != null &&
                user.username != null
        }
}
