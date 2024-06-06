package com.example.basic_weather_forecast.features.home.datasource.network

import com.example.basic_weather_forecast.common.model.ErrorResponse
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel
import com.example.basic_weather_forecast.common.model.Result
import com.google.gson.Gson
import io.reactivex.functions.Function
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class HomeForecastMapper @Inject constructor() :
    Function<Response<HomeForecastResponseModel>, Result<HomeForecastResponseModel>> {

    override fun apply(t: Response<HomeForecastResponseModel>): Result<HomeForecastResponseModel> {
        return when (t.code()) {
            HttpURLConnection.HTTP_OK -> getCurrentWeatherSuccess(t)
            else -> getErrorResponse(t)
        }
    }

    private fun getCurrentWeatherSuccess(
        response: Response<HomeForecastResponseModel>
    ): Result<HomeForecastResponseModel> {
        val body = response.body()
        return if (body != null) {
            Result.Success(body)
        } else {
            Result.Error("Empty response body")
        }
    }

    private fun getErrorResponse(response: Response<HomeForecastResponseModel>): Result<HomeForecastResponseModel> {
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