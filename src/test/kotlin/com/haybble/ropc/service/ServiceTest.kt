package com.haybble.ropc.service


import com.haybble.ropc.db.entities.Users
import com.haybble.ropc.dto.LoginRequest
import com.haybble.ropc.security.TokenManager
import com.haybble.ropc.security.UserDetailsImpl
import com.haybble.ropc.security.UserSecurityService
import com.haybble.ropc.service.serviceImpl.UserServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
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

        val loginRequest = LoginRequest("john", "123456", "clientId")
        Mockito.`when`(userSecurityService.loadUserByUsername("john")).thenReturn(userDetails)
        Mockito.`when`(tokenManager.createToken("john")).thenReturn("testToken")
        val result: Pair<UserDetails, String> = userService.authenticate(loginRequest)
        assertNotNull(result.second)
        assertTrue(result.first.username == user.username)
    }


    @Test
    fun test_clientId_should_not_be_null(){
        var loginRequest = LoginRequest("john","123456","")

            val result =   assertThrows(RuntimeException::class.java) {
            userService.authenticate(loginRequest)
        }

        assertEquals(result.message,"client Id cant be null")
    }


    @Test
    fun test_clientId_should_be_validated(){
        var loginRequest = LoginRequest("john","123456","wrongclientid")
      val result  =  assertThrows(RuntimeException::class.java) {
            userService.authenticate(loginRequest)
        }
        assertEquals(result.message,"client id is wrong")
    }





}