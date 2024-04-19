package com.example.sharemarket.di

import com.example.sharemarket.data.csv.CSVParser
import com.example.sharemarket.data.csv.CompanyListingsParser
import com.example.sharemarket.data.repository.StockRepositoryImpl
import com.example.sharemarket.domain.model.CompanyListing
import com.example.sharemarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(companyListingParser:CompanyListingsParser):CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl:StockRepositoryImpl):StockRepository
}