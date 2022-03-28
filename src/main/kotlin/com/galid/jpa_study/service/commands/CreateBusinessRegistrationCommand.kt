package com.galid.jpa_study.service.commands

import com.galid.jpa_study.dest_domain.AccountType
import com.galid.jpa_study.dest_domain.BusinessRegistrationType

data class CreateBusinessRegistrationCommand(
    var accountType: AccountType,
    var accountId: Long,
    var companyName: String,
    var businessRegistrationType: BusinessRegistrationType?,
    var businessNumber: String,
    var registrationNumber: String?,
    var registrationPaperPhotoId: String?,
    var ownerName: String,
    var businessType: String?,
    var businessItem: String?,
    var address: String?,
    var contact: String?,
    var email: String?,
    var subEmail: String? = null,
) {
}