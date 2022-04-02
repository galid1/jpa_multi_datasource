package com.galid.jpa_study.from_domain

import javax.persistence.*

@Entity
@Table(name ="bl_business_info")
class BusinessRegistrationEntity(
    @Id
    var businessAccountId: Long,

    @Column(nullable = true)
    var name: String?,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    var type: com.galid.jpa_study.dest_domain.entity.BusinessRegistrationType?,

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
}
