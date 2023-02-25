package com.example.exchangeapp.presentation.companies_lists.events

sealed class CompanyListingsEvent {
    object Refresh : CompanyListingsEvent()
    data class SearchQuery(val query: String) : CompanyListingsEvent()
}