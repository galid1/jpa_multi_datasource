package com.galid.jpa_study.from_domain

import javax.persistence.*

enum class BusinessRegistrationType(val value: Int) {
    UNDEFINED(0),
    SELF_EMPLOYED(1), // deprecated
    CORPORATE(2), // deprecated
    GENERAL_TAX_PAYER(3), // 일반 과세자
    SUMMARY_TAX_PAYER(4), // 간이 과세자
    TAX_FREE_PAYER(5), // 면세 과세자 (개인/법인 통합)
    COOPERATE_PAYER(6); // 법인 과세자
}

@Entity
@Table(name = BusinessRegistrationEntity.TABLE_NAME)
class BusinessRegistrationEntity(
    @Id
    var businessAccountId: Long,

    @Column(nullable = true)
    var name: String?,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    var type: BusinessRegistrationType?,

    @Column(nullable = true)
    var registrationNumber: String?,

    @Column(nullable = true)
    var registrationPaperPhotoId: String?,

    @Column(nullable = true)
    var ownerName: String?,

    @Column(nullable = true)
    var industry: String?,

    @Column(nullable = true)
    var businessCategory: String?,

    @Column(nullable = true)
    var address: String?,

    @Column(nullable = true)
    var contact: String?,
) {
    companion object {
        const val TABLE_NAME = "business_registrations"

        fun empty(businessAccountId: Long): BusinessRegistrationEntity {
            return BusinessRegistrationEntity(
                businessAccountId = businessAccountId,
                name = null,
                type = null,
                registrationNumber = null,
                registrationPaperPhotoId = null,
                ownerName = null,
                industry = null,
                businessCategory = null,
                address = null,
                contact = null,
            )
        }
    }
}
