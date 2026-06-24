package br.com.joaodddev.creditscoreengine.config

import br.com.joaodddev.creditscoreengine.infrastructure.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found: $email")

        return org.springframework.security.core.userdetails.User
            .withUsername(user.email)
            .password(user.password)
            .roles(user.role.name)
            .build()
    }
}