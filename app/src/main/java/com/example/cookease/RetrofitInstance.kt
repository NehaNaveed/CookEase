package com.example.cookease

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

object RetrofitInstance {


    private val retrofit by lazy{

        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val mealsInterface : MealsInterface by lazy {
        retrofit.create(MealsInterface::class.java)
    }
}