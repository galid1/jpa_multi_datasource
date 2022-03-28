package com.galid.jpa_study.dest_domain

import com.galid.jpa_study.dest_domain.AccountType
import com.galid.jpa_study.dest_domain.OwnBusinessRegistrationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OwnBusinessRegistrationRepository: JpaRepository<OwnBusinessRegistrationEntity, Long> {
    fun findAllByAccountTypeAndAccountId(accountType: AccountType, accountId: Long): List<OwnBusinessRegistrationEntity>
}