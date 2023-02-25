package com.example.exchangeapp.data.local

import androidx.room.*
import java.nio.charset.CodingErrorAction.REPLACE


@Dao
interface ExchangeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCompaniesLists(companyListingEntity: List<CompanyListingEntity>)

   @Query("DELETE FROM CompanyListingEntity")
  suspend fun deleteCompaniesList()

  @Query(

      """
             SELECT *
              FROM companylistingentity
              WHERE LOWER(name) LIKE '%'  || LOWER(:query)  || '%' OR
              UPPER(:query) == symbol 
              """
  )
  suspend fun searchOnCompaniesList(query: String) : List<CompanyListingEntity>


}