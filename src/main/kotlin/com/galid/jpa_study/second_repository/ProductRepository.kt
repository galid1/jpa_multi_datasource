package com.galid.jpa_study.second_repository

import com.galid.jpa_study.entity.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long> {
}