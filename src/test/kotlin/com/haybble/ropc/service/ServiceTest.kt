package com.haybble.ropc.service

import com.haybble.ropc.db.entities.Users
import com.haybble.ropc.db.repository.UserRepository
import com.haybble.ropc.dto.LoginRequest
import com.haybble.ropc.security.TokenManager
import com.haybble.ropc.security.UserDetailsImpl
import com.haybble.ropc.security.UserSecurityService
import com.haybble.ropc.service.serviceImpl.UserServiceImpl


import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetails

@ExtendWith(MockitoExtension::class)
class ServiceTest {

    @Mock
    lateinit var tokenManager: TokenManager

    @Mock
    lateinit var userSecurityService: UserSecurityService

    @InjectMocks
    lateinit var userService: UserServiceImpl




    @Test
    fun test_user_can_login_and_get_token() {
        var user: Users = Users("john", "123456", 1)

        val userDetails = UserDetailsImpl(
            user
        )

        val loginRequest = LoginRequest("john", "123456")
        Mockito.`when`(userSecurityService.loadUserByUsername("john")).thenReturn(userDetails)
        Mockito.`when`(tokenManager.createToken("john")).thenReturn("testToken")
        val result: Pair<UserDetails, String> = userService.authenticate(loginRequest)
        assertNotNull(result.second)
        assertTrue(result.first.username == user.username)
    }



}