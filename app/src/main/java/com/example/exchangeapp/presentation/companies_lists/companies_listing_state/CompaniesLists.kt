package com.example.exchangeapp.presentation.companies_lists.companies_listing_state

import com.example.exchangeapp.domain.model.CompanyListing

data class CompaniesLists(
    val companyList : List<CompanyListing> = emptyList(),
    val  isLoading : Boolean = false,
    val error : String = "",
    val refreshScreen : Boolean = false,
    val searchQuery : String = ""
)