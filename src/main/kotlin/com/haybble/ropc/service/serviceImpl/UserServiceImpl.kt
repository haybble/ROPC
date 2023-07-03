package com.haybble.ropc.service.serviceImpl

import com.haybble.ropc.dto.LoginRequest
import com.haybble.ropc.security.TokenManager
import com.haybble.ropc.security.UserSecurityService
import com.haybble.ropc.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service
class UserServiceImpl(

    var userSecurityService: UserSecurityService,

    var tokenManager: TokenManager


    ) : UserService {


    override fun authenticate(loginRequest: LoginRequest): Pair<UserDetails, String> {
      val clientID =  loginRequest.clientId
        if(clientID.isNullOrEmpty() ) {
            throw RuntimeException("client Id cant be null")
        }

        validateClientId(clientID)

        val username: String = loginRequest.username
        val userDetails: UserDetails = userSecurityService.loadUserByUsername(username)!!
        userSecurityService.check(loginRequest,userDetails)
            val token: String = tokenManager.createToken(username)
            return Pair(userDetails, token)

    }

    private fun validateClientId(clientID: String)  {
            if (clientID == "clientId") {
                return
            }
            throw RuntimeException("client id is wrong")
    }



}
