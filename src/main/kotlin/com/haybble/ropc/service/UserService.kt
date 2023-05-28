package com.haybble.ropc.service

import com.haybble.ropc.db.entities.Users
import com.haybble.ropc.dto.LoginRequest
import org.springframework.security.core.userdetails.UserDetails

  interface UserService {
      fun authenticate(loginRequest: LoginRequest): Pair<UserDetails, String>


}
