package com.haybble.ropc.security

import com.haybble.ropc.db.repository.UserRepository
import com.haybble.ropc.dto.LoginRequest
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.BadCredentialsException

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserSecurityService(val userRepository: UserRepository,val passwordEncoder: PasswordEncoder):UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("$username not found")
        return UserDetailsImpl(
           user
        )
    }

    fun check(loginRequest: LoginRequest, userDetails: UserDetails): Boolean {
        if (passwordEncoder.matches((loginRequest.password), userDetails.password)) {
            return true
        }
        throw BadCredentialsException("bad credentials")
    }


}