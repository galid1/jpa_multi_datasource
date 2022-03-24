package com.galid.jpa_study.repository

import com.galid.jpa_study.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

}