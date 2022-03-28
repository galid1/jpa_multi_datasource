package com.galid.jpa_study.repository

import com.galid.jpa_study.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

}