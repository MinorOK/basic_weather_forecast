package com.example.basic_weather_forecast.features.whole_day.datasource.network

import android.util.Log
import com.example.basic_weather_forecast.features.home.datasource.model.ErrorResponse
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayWeatherResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.Result
import com.google.gson.Gson
import io.reactivex.functions.Function
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class WholeDayForecastMapper @Inject constructor() :
    Function<Response<WholeDayWeatherResponseModel>, Result<WholeDayWeatherResponseModel>> {

    override fun apply(t: Response<WholeDayWeatherResponseModel>): Result<WholeDayWeatherResponseModel> {
        return when (t.code()) {
            HttpURLConnection.HTTP_OK -> getWholeDayWeatherSuccess(t)
            else -> getErrorResponse(t)
        }
    }

    private fun getWholeDayWeatherSuccess(
        response: Response<WholeDayWeatherResponseModel>
    ): Result<WholeDayWeatherResponseModel> {
        Log.d("ForecastLog", response.body().toString())
        val body = response.body()
        return if (body != null) {
            Result.Success(body)
        } else {
            Result.Error("Empty response body")
        }
    }

    private fun getErrorResponse(response: Response<WholeDayWeatherResponseModel>): Result<WholeDayWeatherResponseModel> {
        val errorBody = response.errorBody()?.string()
        return if (errorBody != null) {
            try {
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                Log.d("ForecastLog", errorResponse.message)
                Result.Error(errorResponse.message)
            } catch (e: Exception) {
                Result.Error("Unknown error")
            }
        } else {
            Result.Error("Unknown error")
        }
    }
}