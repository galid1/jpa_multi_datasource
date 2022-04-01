package com.galid.jpa_study.from_domain

import javax.persistence.*

@Entity
@Table(name ="bl_business_info")
class BusinessRegistrationEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= null,

    var advertiserId: Long,

    var address: String?,

    var businessItem: String?,

    var businessType: String?,

    var businessNumber: String,

    var ceoName: String?,

    var companyName: String?,

    var email: String?,

    var subEmail: String?,
) {
}
