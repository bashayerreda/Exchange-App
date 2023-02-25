package com.example.exchangeapp.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CompanyListingEntity::class],
    version = 1)
abstract class ExchangeDataBase : RoomDatabase(){
     abstract val dao : ExchangeDao
}