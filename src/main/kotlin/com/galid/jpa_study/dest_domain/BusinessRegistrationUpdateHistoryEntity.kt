package com.galid.jpa_study.dest_domain

import com.galid.jpa_study.dest_domain.BusinessRegistrationUpdateHistoryEntity.Companion.TABLE_NAME
import javax.persistence.*

@Entity
@Table(
    name = TABLE_NAME,
    indexes = [
        Index(name = "ix_businessregistrationid_createdat", columnList = "businessRegistrationId, createdAt")
    ]
)
class BusinessRegistrationUpdateHistoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var businessRegistrationId: Long,

    @Column(columnDefinition = "json")
    var snapshot: String,

    var updaterAccountType: String,

    var updaterAccountId: Long
): Timestamp() {
    companion object {
        const val TABLE_NAME = "business_registration_update_history"

        fun of(
            businessRegistrationId: Long,
            snapshot: String,
            updaterAccountType: AccountType,
            updaterAccountId: Long
        ): BusinessRegistrationUpdateHistoryEntity {
            return BusinessRegistrationUpdateHistoryEntity(
                businessRegistrationId = businessRegistrationId,
                snapshot = snapshot,
                updaterAccountType = updaterAccountType.toString(),
                updaterAccountId = updaterAccountId
            )
        }
    }
}