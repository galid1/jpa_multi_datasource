package com.galid.jpa_study.service

import com.galid.jpa_study.service.commands.CreateBusinessRegistrationCommand
import com.fasterxml.jackson.databind.ObjectMapper
import com.galid.jpa_study.dest_domain.*
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class BusinessRegistrationCommandService(
//    private val businessRegistrationRepository: BusinessRegistrationRepository,
//    private val ownBusinessRegistrationRepository: OwnBusinessRegistrationRepository,
//    private val businessRegistrationUpdateHistoryRepository: BusinessRegistrationUpdateHistoryRepository,
//    private val objectMapper: ObjectMapper
) {
//    fun createBusinessRegistration(command: CreateBusinessRegistrationCommand) {
//        val newBusinessRegistration = businessRegistrationRepository.save(
//            BusinessRegistrationEntity.of(
//                companyName = command.companyName,
//                businessRegistrationType = command.businessRegistrationType,
//                businessNumber = command.businessNumber,
//                registrationNumber = command.registrationNumber,
//                registrationPaperPhotoId = command.registrationPaperPhotoId,
//                ownerName = command.ownerName,
//                businessType = command.businessType,
//                businessItem = command.businessItem,
//                address = command.address,
//                contact = command.contact,
//                email = command.email,
//                subEmail = command.subEmail,
//            )
//        )
//
//        ownBusinessRegistrationRepository.save(
//            OwnBusinessRegistrationEntity(
//                businessRegistrationId = newBusinessRegistration.id!!,
//                accountType = command.accountType,
//                accountId = command.accountId
//            )
//        )
//
//        businessRegistrationUpdateHistoryRepository.save(
//            BusinessRegistrationUpdateHistoryEntity.of(
//                businessRegistrationId = newBusinessRegistration.id!!,
//                snapshot = objectMapper.writeValueAsString(newBusinessRegistration),
//                updaterAccountType = command.accountType,
//                updaterAccountId = command.accountId
//            )
//        )
//    }
}