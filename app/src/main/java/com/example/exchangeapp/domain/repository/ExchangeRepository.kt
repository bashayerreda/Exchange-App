package com.example.exchangeapp.domain.repository

import com.example.exchangeapp.domain.model.CompanyListing
import com.example.exchangeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query


interface ExchangeRepository {
    suspend fun getData(
        returnDataFromAPi : Boolean,
        query: String
    ) : Flow<Resource<List<CompanyListing>>>
}