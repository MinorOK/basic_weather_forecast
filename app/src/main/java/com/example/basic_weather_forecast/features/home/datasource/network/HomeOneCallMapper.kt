package com.example.basic_weather_forecast.features.home.datasource.network

import com.example.basic_weather_forecast.features.home.datasource.model.ErrorResponse
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.Result
import com.google.gson.Gson
import io.reactivex.functions.Function
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class HomeOneCallMapper @Inject constructor() :
    Function<Response<HomeOneCallResponseModel>, Result<HomeOneCallResponseModel>> {

    override fun apply(t: Response<HomeOneCallResponseModel>): Result<HomeOneCallResponseModel> {
        return when (t.code()) {
            HttpURLConnection.HTTP_OK -> getOneCallSuccess(t)
            else -> getErrorResponse(t)
        }
    }

    private fun getOneCallSuccess(
        response: Response<HomeOneCallResponseModel>
    ): Result<HomeOneCallResponseModel> {
        val body = response.body()
        return if (body != null) {
            Result.Success(body)
        } else {
            Result.Error("Empty response body")
        }
    }

    private fun getErrorResponse(response: Response<HomeOneCallResponseModel>): Result<HomeOneCallResponseModel> {
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