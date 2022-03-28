package com.galid.jpa_study.dest_domain.entity

import com.galid.jpa_study.dest_domain.entity.CompanyStatus.OPEN
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*
import javax.persistence.EnumType.STRING

enum class BusinessRegistrationType(val value: Int) {
    UNDEFINED(0),
    GENERAL_TAX_PAYER(1), // 일반 과세자
    SUMMARY_TAX_PAYER(2), // 간이 과세자
    TAX_FREE_PAYER(3), // 면세 과세자 (개인/법인 통합)
    COOPERATE_PAYER(4), // 법인 과세자
    SELF_EMPLOYED(5),
    CORPORATE(6);

}

enum class CompanyStatus{
    OPEN, // 영업
    SHUT_DOWN, // 폐업
    TEMPORARILY_CLOSE; // 휴업
}

@Entity
@Table(
    name = BusinessRegistrationEntity.TABLE_NAME,
    uniqueConstraints = [
        UniqueConstraint(
            name = "ux_businessnumber",
            columnNames = ["businessNumber"]
        )
    ]
)
@SQLDelete(sql = "UPDATE ${BusinessRegistrationEntity.TABLE_NAME} SET ${BusinessRegistrationEntity.SOFT_DELETE_COLUMN} = current_timestamp() where id = ?")
class BusinessRegistrationEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = true)
    var companyName: String?,

    @Column(nullable = true)
    @Enumerated(STRING)
    var businessRegistrationType: BusinessRegistrationType?,

    // 사업자 번호
    @Column(nullable = true)
    var businessNumber: String?,

    // 종사업자 번호
    @Column(nullable = true)
    var registrationNumber: String?,

    @Column(nullable = true)
    var registrationPaperPhotoId: String?,

    @Column(nullable = true)
    var ownerName: String?,

    @Column(nullable = true)
    var businessType: String?,

    @Column(nullable = true)
    var businessItem: String?,

    @Column(nullable = true)
    var address: String?,

    @Column(nullable = true)
    var contact: String?,

    @Column(nullable = true)
    var email: String?,

    @Column(nullable = true)
    var subEmail: String? = null,

    @Column(nullable = true)
    @Enumerated(STRING)
    var companyStatus: CompanyStatus = OPEN,

    @Column(nullable = true)
    var companyStatusDate: LocalDateTime = now(),

    @Column(nullable = true)
    var deletedAt: LocalDateTime? = null,
) {
    val deleted: Boolean
        get() = deletedAt != null

    fun update(
        newCompanyName: String?,
        newBusinessRegistrationType: BusinessRegistrationType?,
        newOwnerName: String?,
        newBusinessType: String?,
        newBusinessItem: String?,
        newAddress: String?,
        newContact: String?,
        newEmail: String?,
        newSubEmail: String?,
        newCompanyStatus: CompanyStatus?
    ) {
        newCompanyName?.run { companyName = newCompanyName }
        newBusinessRegistrationType?.run { businessRegistrationType = newBusinessRegistrationType }
        newOwnerName?.run { ownerName = newOwnerName }
        newBusinessType?.run { businessType = newBusinessType }
        newBusinessItem?.run { businessItem = newBusinessItem }
        newAddress?.run { address = newAddress }
        newContact?.run { contact = newContact }
        newEmail?.run { email = newEmail }
        newSubEmail?.run { subEmail = newSubEmail }
        newCompanyStatus?. run {
            if (companyStatus != newCompanyStatus) {
                companyStatusDate = now()
                companyStatus = newCompanyStatus
            }
        }
    }

    companion object {
        const val TABLE_NAME = "business_registrations"
        const val SOFT_DELETE_COLUMN = "deleted_at"

        fun of(
            companyName: String,
            businessRegistrationType: BusinessRegistrationType?,
            businessNumber: String,
            registrationNumber: String?,
            registrationPaperPhotoId: String?,
            ownerName: String,
            businessType: String?,
            businessItem: String?,
            address: String?,
            contact: String?,
            email: String?,
            subEmail: String?
        ): BusinessRegistrationEntity {
            return BusinessRegistrationEntity(
                companyName = companyName,
                businessRegistrationType = businessRegistrationType,
                businessNumber = businessNumber,
                registrationNumber = registrationNumber,
                registrationPaperPhotoId = registrationPaperPhotoId,
                ownerName = ownerName,
                businessType = businessType,
                businessItem = businessItem,
                address = address,
                contact = contact,
                email = email,
                subEmail = subEmail,
            )
        }
    }
}
