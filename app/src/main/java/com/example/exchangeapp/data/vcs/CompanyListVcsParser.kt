package com.example.exchangeapp.data.vcs

import java.io.InputStream

interface CompanyListVcsParser<T> {
    suspend fun parse(stream: InputStream): List<T>
}