package com.galid.jpa_study.dest_domain.repo

import com.galid.jpa_study.dest_domain.entity.AccountType
import com.galid.jpa_study.dest_domain.entity.OwnBusinessRegistrationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OwnBusinessRegistrationRepository: JpaRepository<OwnBusinessRegistrationEntity, Long> {
    fun findAllByAccountTypeAndAccountId(accountType: AccountType, accountId: Long): List<OwnBusinessRegistrationEntity>
}