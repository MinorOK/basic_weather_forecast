package com.example.basic_weather_forecast.features.home.datasource.network

import com.example.basic_weather_forecast.features.home.datasource.model.ErrorResponse
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.Result
import com.google.gson.Gson
import io.reactivex.functions.Function
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class HomeGeocodeMapper @Inject constructor() :
    Function<Response<List<GeocodingResponseModel>>, Result<List<GeocodingResponseModel>>> {

    override fun apply(t: Response<List<GeocodingResponseModel>>): Result<List<GeocodingResponseModel>> {
        return when (t.code()) {
            HttpURLConnection.HTTP_OK -> getGeocodeSuccess(t)
            else -> getErrorResponse(t)
        }
    }

    private fun getGeocodeSuccess(
        response: Response<List<GeocodingResponseModel>>
    ): Result<List<GeocodingResponseModel>> {
        val body = response.body()
        return if (body != null) {
            Result.Success(body)
        } else {
            Result.Error("Empty response body")
        }
    }

    private fun getErrorResponse(response: Response<List<GeocodingResponseModel>>): Result<List<GeocodingResponseModel>> {
        val errorBody = response.errorBody()?.string()
        return if (errorBody != null) {
            try {
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                Result.Error(errorResponse.message)
            } catch (e: Exception) {
                Result.Error("Unknown error")
            }
        } else {
            Result.Error("Unknown error")
        }
    }
}