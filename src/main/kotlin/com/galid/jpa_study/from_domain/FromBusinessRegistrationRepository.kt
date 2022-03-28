package com.galid.jpa_study.from_domain

import org.springframework.data.jpa.repository.JpaRepository

interface FromBusinessRegistrationRepository : JpaRepository<BusinessRegistrationEntity, Long>
