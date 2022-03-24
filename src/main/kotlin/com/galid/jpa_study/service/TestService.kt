package com.galid.jpa_study.service

import com.galid.jpa_study.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class TestService(
    private val userRepository: UserRepository,
) {

}