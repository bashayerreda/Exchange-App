package com.example.exchangeapp.data.mapper

import com.example.exchangeapp.data.local.CompanyListingEntity
import com.example.exchangeapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing {
    return CompanyListing(name = name , symbol = symbol , exchange = exchange)
}
fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity {
    return CompanyListingEntity(name=name,symbol = symbol , exchange = exchange)
}