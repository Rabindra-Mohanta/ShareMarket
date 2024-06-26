package com.example.sharemarket.data.mapper

import com.example.sharemarket.data.local.CompanyListingEntity
import com.example.sharemarket.data.remote.dto.CompanyInfoDto
import com.example.sharemarket.domain.model.CompanyInfo
import com.example.sharemarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name = name, symbol = symbol, exchange = exchange)
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name = name, symbol = symbol, exchange = exchange)
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}

