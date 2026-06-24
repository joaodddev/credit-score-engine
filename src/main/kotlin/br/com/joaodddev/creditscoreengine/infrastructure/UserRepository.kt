package br.com.joaodddev.creditscoreengine.infrastructure

import br.com.joaodddev.creditscoreengine.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}