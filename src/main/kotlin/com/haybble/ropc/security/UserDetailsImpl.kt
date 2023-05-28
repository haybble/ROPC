package com.haybble.ropc.security

import com.haybble.ropc.db.entities.Users
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


class UserDetailsImpl(user:Users) : UserDetails {
    private val username: String
    private val password: String
    private val authorities: Collection<GrantedAuthority?>

    init {
        username = user.username
        password = user.password
        authorities =  ArrayList()
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}


