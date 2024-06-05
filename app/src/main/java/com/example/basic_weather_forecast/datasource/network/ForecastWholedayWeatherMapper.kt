package com.example.basic_weather_forecast.datasource.network

import android.util.Log
import com.example.basic_weather_forecast.datasource.model.ErrorResponse
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherResponseModel
import com.example.basic_weather_forecast.datasource.model.Result
import com.google.gson.Gson
import io.reactivex.functions.Function
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class ForecastWholeDayWeatherMapper @Inject constructor() :
    Function<Response<ForecastWholeDayWeatherResponseModel>, Result<ForecastWholeDayWeatherResponseModel>> {

    override fun apply(t: Response<ForecastWholeDayWeatherResponseModel>): Result<ForecastWholeDayWeatherResponseModel> {
        return when (t.code()) {
            HttpURLConnection.HTTP_OK -> getWholeDayWeatherSuccess(t)
            else -> getErrorResponse(t)
        }
    }

    private fun getWholeDayWeatherSuccess(
        response: Response<ForecastWholeDayWeatherResponseModel>
    ): Result<ForecastWholeDayWeatherResponseModel> {
        Log.d("Minor", response.body().toString())
        val body = response.body()
        return if (body != null) {
            Result.Success(body)
        } else {
            Result.Error("Empty response body")
        }
    }

    private fun getErrorResponse(response: Response<ForecastWholeDayWeatherResponseModel>): Result<ForecastWholeDayWeatherResponseModel> {
        val errorBody = response.errorBody()?.string()
        return if (errorBody != null) {
            try {
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                Log.d("Minor", errorResponse.message)
                Result.Error(errorResponse.message)
            } catch (e: Exception) {
                Result.Error("Unknown error")
            }
        } else {
            Result.Error("Unknown error")
        }
    }
}