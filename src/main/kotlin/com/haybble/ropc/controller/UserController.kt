package com.haybble.ropc.controller

import com.haybble.ropc.dto.LoginRequest
import com.haybble.ropc.dto.LoginResponse
import com.haybble.ropc.service.UserService

import org.springframework.http.ResponseEntity

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class UserController (
   private  val userService: UserService

    ){

    @GetMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val (userDetails: UserDetails, token: String) = userService.authenticate(loginRequest)
        return ResponseEntity.ok(LoginResponse(userDetails.username, token))
    }




  }