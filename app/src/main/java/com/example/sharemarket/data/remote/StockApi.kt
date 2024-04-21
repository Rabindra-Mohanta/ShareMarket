package com.example.sharemarket.data.remote

import com.example.sharemarket.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query("apikey") apiKey:String = API_KEY):ResponseBody
    companion object
    {
        const val API_KEY = "DJHHNVOBGXNISGKW"
        const val BASE_URL = "https://www.alphavantage.co"
    }
    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(@Query("symbol") symbol:String,@Query("apikey") apiKey: String = API_KEY):ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(@Query("symbol") symbol: String,@Query("apikey") apiKey: String = API_KEY):CompanyInfoDto



}