package com.galid.jpa_study.second_repository

import com.galid.jpa_study.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserSecondRepository: JpaRepository<User, Long> {
}