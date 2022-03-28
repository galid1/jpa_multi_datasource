package com.galid.jpa_study.dest_domain.entity

import com.galid.jpa_study.dest_domain.Timestamp
import com.galid.jpa_study.dest_domain.entity.OwnBusinessRegistrationEntity.Companion.TABLE_NAME
import javax.persistence.*
import javax.persistence.EnumType.STRING

enum class AccountType {
    // 당근 개인계정
    HOIAN,

    // 비즈니스 계정
    BUSINESS_USER,

    // 비즈니스 프로필
    BUSINESS_ACCOUNT,
}

@Entity
@Table(
    name = TABLE_NAME,
    indexes = [
        Index(name = "ux_accounttype_accountid", columnList = "accountType, accountId")
    ]
)
class OwnBusinessRegistrationEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var businessRegistrationId: Long,

    var accountId: Long,

    @Enumerated(STRING)
    var accountType: AccountType,
): Timestamp() {

    companion object {
        const val TABLE_NAME = "own_business_registrations"
    }
}