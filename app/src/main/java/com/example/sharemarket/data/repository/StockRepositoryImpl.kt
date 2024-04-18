package com.example.sharemarket.data.repository

import android.net.http.HttpEngine
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.sharemarket.data.local.StockDatabase
import com.example.sharemarket.data.mapper.toCompanyListing
import com.example.sharemarket.data.remote.StockApi
import com.example.sharemarket.domain.model.CompanyListing
import com.example.sharemarket.domain.repository.StockRepository
import com.example.sharemarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(val api: StockApi, val db: StockDatabase) :
    StockRepository {
    private val dao = db.dao

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyListing(query)
            emit(Resource.Success(data = localListing.map { it.toCompanyListing() }))
            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldjustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldjustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try {
                val response = api.getListings()
                

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }

        }
    }
}