package com.example.exchangeapp.data.repository

import com.example.exchangeapp.data.handle_errors.handleResponse
import com.example.exchangeapp.data.local.ExchangeDao
import com.example.exchangeapp.data.mapper.toCompanyListing
import com.example.exchangeapp.data.mapper.toCompanyListingEntity
import com.example.exchangeapp.data.remote.ExchangApi
import com.example.exchangeapp.data.vcs.CompanyListVcsParser
import com.example.exchangeapp.domain.model.CompanyListing
import com.example.exchangeapp.domain.repository.ExchangeRepository
import com.example.exchangeapp.utils.Constants
import com.example.exchangeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepoImp @Inject constructor(private val api : ExchangApi , private val dao : ExchangeDao,private val companyVcsParser : CompanyListVcsParser<CompanyListing>) : ExchangeRepository {
    override suspend fun getData(
        returnDataFromAPi: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val dataFromDao = dao.searchOnCompaniesList(query)
            emit(Resource.Success(dataFromDao.map { it.toCompanyListing() }))
            val isDataBaseEmpty = dataFromDao.isEmpty() && query.isBlank()
            val dataBaseNotEmpty = dataFromDao.isNotEmpty() && query.isNotBlank()
            val cacheDataFromApi = !isDataBaseEmpty && !returnDataFromAPi
            if (cacheDataFromApi) {
                emit(Resource.Loading(false))
                this@flow
            }
            val dataFromAPi = try {
                val response = api.getComapnyList()
               companyVcsParser.parse(response.byteStream())
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString(),null))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message().toString(),null))
                null
            }

            dataFromAPi?.let { listings ->
                dao.deleteCompaniesList()
                dao.insertCompaniesLists(
                   listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(data = dao.searchOnCompaniesList("").map { it.toCompanyListing() }))
                emit(Resource.Loading(false))

            }
        }
    }
}