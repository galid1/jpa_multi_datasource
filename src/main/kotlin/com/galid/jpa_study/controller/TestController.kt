package com.galid.jpa_study.controller

import com.galid.jpa_study.service.TestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val testService: TestService,
) {
    @GetMapping("/1")
    fun t1() {
        println("receive")
    }
}