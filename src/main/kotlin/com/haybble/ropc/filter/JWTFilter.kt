package com.haybble.ropc.filter

import com.haybble.ropc.security.TokenManager
import com.haybble.ropc.security.UserSecurityService

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTFilter: OncePerRequestFilter() {
    lateinit var tokenManager: TokenManager
    lateinit var userService: UserSecurityService


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val  `authorization-header` = request.getHeader("Authorization")

        var username: String? = null
        var jwt: String? = null

        if (`authorization-header` != null && `authorization-header`.startsWith("Bearer ")) {
            jwt = `authorization-header`.substring(7)
            username = tokenManager.extractUsername(jwt)
        }
        try {
            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails: UserDetails = this.userService.loadUserByUsername(username)!!
                if (jwt?.let { tokenManager.validateToken(it, userDetails) } == true) {
                    val usernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        filterChain.doFilter(request, response)
    }
}