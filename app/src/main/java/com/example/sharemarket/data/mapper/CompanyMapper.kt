package com.example.sharemarket.data.mapper

import com.example.sharemarket.data.local.CompanyListingEntity
import com.example.sharemarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing():CompanyListing
{
    return CompanyListing(name = name,symbol =symbol,exchange = exchange )
}

fun CompanyListingEntity.toCompanyListingEntity():CompanyListingEntity
{
    return CompanyListingEntity(name = name,symbol =symbol,exchange = exchange )
}

