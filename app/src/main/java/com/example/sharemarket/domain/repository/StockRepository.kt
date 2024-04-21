package com.example.sharemarket.domain.repository

import com.example.sharemarket.domain.model.CompanyInfo
import com.example.sharemarket.domain.model.CompanyListing
import com.example.sharemarket.domain.model.IntradayInfo
import com.example.sharemarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository
{
    suspend fun getCompanyListings(fetchFromRemote:Boolean,query:String):Flow<Resource<List<CompanyListing>>>
 suspend fun getIntradayInfo(symbol:String):Resource<List<IntradayInfo>>
 suspend fun getCompanyInfo(symbol: String):Resource<CompanyInfo>
}
