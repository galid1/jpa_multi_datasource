package com.galid.jpa_study.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.galid.jpa_study.dest_domain.entity.BusinessRegistrationEntity
import com.galid.jpa_study.dest_domain.entity.BusinessRegistrationUpdateHistoryEntity
import com.galid.jpa_study.dest_domain.entity.OwnBusinessRegistrationEntity
import com.galid.jpa_study.dest_domain.repo.BusinessRegistrationRepository
import com.galid.jpa_study.dest_domain.repo.BusinessRegistrationUpdateHistoryRepository
import com.galid.jpa_study.dest_domain.repo.OwnBusinessRegistrationRepository
import com.galid.jpa_study.service.commands.CreateBusinessRegistrationCommand
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class BusinessRegistrationCommandService(
    private val businessRegistrationRepository: BusinessRegistrationRepository,
    private val ownBusinessRegistrationRepository: OwnBusinessRegistrationRepository,
    private val businessRegistrationUpdateHistoryRepository: BusinessRegistrationUpdateHistoryRepository,
    private val objectMapper: ObjectMapper,
) {
    fun createBusinessRegistration(command: CreateBusinessRegistrationCommand) {
        val businessRegistrationEntity = businessRegistrationRepository.findByBusinessNumber(command.businessNumber)

        if (businessRegistrationEntity == null) {
            val newBusinessRegistration = BusinessRegistrationEntity.of(
                companyName = command.companyName,
                businessRegistrationType = command.businessRegistrationType,
                businessNumber = command.businessNumber,
                registrationNumber = command.registrationNumber,
                registrationPaperPhotoId = command.registrationPaperPhotoId,
                ownerName = command.ownerName,
                businessType = command.businessType,
                businessItem = command.businessItem,
                address = command.address,
                contact = command.contact,
                email = command.email,
                subEmail = command.subEmail,
            )

            businessRegistrationRepository.save(
                newBusinessRegistration
            )

            ownBusinessRegistrationRepository.save(
                OwnBusinessRegistrationEntity(
                    businessRegistrationId = newBusinessRegistration.id!!,
                    accountType = command.accountType,
                    accountId = command.accountId
                )
            )

            businessRegistrationUpdateHistoryRepository.save(
                BusinessRegistrationUpdateHistoryEntity.of(
                    businessRegistrationId = newBusinessRegistration.id!!,
                    snapshot = objectMapper.writeValueAsString(newBusinessRegistration),
                    updaterAccountType = command.accountType,
                    updaterAccountId = command.accountId
                )
            )
        }
        else {
            ownBusinessRegistrationRepository.save(
                OwnBusinessRegistrationEntity(
                    businessRegistrationId = businessRegistrationEntity.id!!,
                    accountType = command.accountType,
                    accountId = command.accountId
                )
            )
        }
    }
}