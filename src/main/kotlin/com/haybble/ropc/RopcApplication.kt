package com.haybble.ropc

import com.haybble.ropc.db.entities.Users
import com.haybble.ropc.db.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@SpringBootApplication
@EnableAutoConfiguration
class RopcApplication {


	@Bean
	fun runner(repository: UserRepository,passwordEncoder: PasswordEncoder) = CommandLineRunner {

		val users = Users("henry", passwordEncoder.encode("123"))

		repository.save(users)

	}
	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()}
}

	fun main(args: Array<String>) {
		runApplication<RopcApplication>(*args)
	}
