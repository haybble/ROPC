package com.haybble.ropc.db.entities



import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
 class Users( @Column(nullable = false)
              val username: String,
              @Column(nullable = false)
              var password: String,
              @Id
              @GeneratedValue(strategy = GenerationType.IDENTITY)
              val id: Int?=null)
