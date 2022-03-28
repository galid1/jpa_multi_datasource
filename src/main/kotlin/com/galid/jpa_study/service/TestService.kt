package com.galid.jpa_study.service

import com.galid.jpa_study.entity.product.Product
import com.galid.jpa_study.entity.user.User
import com.galid.jpa_study.repository.UserRepository
import com.galid.jpa_study.second_repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class TestService(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {
    fun t() {
        userRepository.save(User())
    }

    fun t2() {
        productRepository.save(Product(price = 1000))
    }
}