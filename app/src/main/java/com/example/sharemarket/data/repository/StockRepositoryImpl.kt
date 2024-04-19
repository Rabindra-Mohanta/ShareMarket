package com.example.sharemarket.data.repository
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.sharemarket.data.csv.CSVParser
import com.example.sharemarket.data.local.StockDatabase
import com.example.sharemarket.data.mapper.toCompanyListing
import com.example.sharemarket.data.mapper.toCompanyListingEntity
import com.example.sharemarket.data.remote.StockApi
import com.example.sharemarket.domain.model.CompanyListing
import com.example.sharemarket.domain.repository.StockRepository
import com.example.sharemarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(private val api:StockApi,private val db:StockDatabase,private val companyListingsParser:CSVParser<CompanyListing>):StockRepository
{
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
            if(shouldjustLoadFromCache)
            {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing =
                try {
                    val response = api.getListings()
                    companyListingsParser.parse(response.byteStream())
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null

                }
            catch (e:HttpException)
            {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListing?.let { listings->
                dao.clearCompanyListings()
                dao.insertCompanyListings( listings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(data = dao.searchCompanyListing("").map { it.toCompanyListing() }))
                emit(Resource.Loading(false))


            }


        }
    }

}

