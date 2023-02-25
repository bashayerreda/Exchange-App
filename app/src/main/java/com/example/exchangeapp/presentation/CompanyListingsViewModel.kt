package com.example.exchangeapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exchangeapp.domain.repository.ExchangeRepository

import com.example.exchangeapp.presentation.companies_lists.companies_listing_state.CompaniesLists
import com.example.exchangeapp.presentation.companies_lists.events.CompanyListingsEvent
import com.example.exchangeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.annotation.Inherited
import javax.inject.Inject


@HiltViewModel
class CompanyListingsViewModel @Inject constructor(private val exchangeRepo : ExchangeRepository) : ViewModel(){

    var state by mutableStateOf(CompaniesLists())
    private var searchJob: Job? = null

    fun triggerEvents(events : CompanyListingsEvent) {
        when(events){
            is CompanyListingsEvent.Refresh -> {
                getDataFromRepo(shouldTakeFromApi = true)
            }
            is CompanyListingsEvent.SearchQuery -> {
                    state = state.copy(searchQuery = events.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                   getDataFromRepo()
                }
            }
        }


    }

    fun getDataFromRepo(query : String = state.searchQuery.lowercase() , shouldTakeFromApi : Boolean = false){
        viewModelScope.launch {
            exchangeRepo.getData(shouldTakeFromApi,query).collect{ results ->
                when(results){
                    is Resource.Success -> {
                       results.data.let {  companiesList ->
                           state = state.copy(companyList = companiesList!!)
                       }
                    }
                    is Resource.Error -> {
                    state = state.copy(error = results.message.toString())
                    }
                    is Resource.Loading -> {
                       state = state.copy(isLoading = true)
                    }
                }

            }
        }
    }
}