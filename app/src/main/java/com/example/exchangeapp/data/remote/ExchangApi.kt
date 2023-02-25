package com.example.exchangeapp.data.remote

import com.example.exchangeapp.utils.Constants
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangApi {
    @GET("\"query?function=LISTING_STATUS\"")
    suspend fun getComapnyList(@Query("apikey")apiKey : String = Constants.API_KEY
    ): ResponseBody
}