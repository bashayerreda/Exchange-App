package com.example.exchangeapp.domain.repository

import com.example.exchangeapp.domain.model.CompanyListing
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query


interface ExchangeRepository {
    suspend fun getData(
        returnDataFromAPi : Boolean,
        query: String
    ) : Flow<List<CompanyListing>>
}