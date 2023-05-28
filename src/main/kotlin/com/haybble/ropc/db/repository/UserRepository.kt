package com.haybble.ropc.db.repository

import com.haybble.ropc.db.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
 interface UserRepository : JpaRepository<Users,Int>{
  fun findByUsername(username:String): Users?

 }
