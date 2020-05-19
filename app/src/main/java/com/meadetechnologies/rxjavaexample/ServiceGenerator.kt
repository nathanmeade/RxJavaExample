package com.meadetechnologies.rxjavaexample

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    val retrofit = retrofitBuilder.build()

    val requestApi = retrofit.create(RequestApi::class.java)

    fun getrequestApi(): RequestApi {
        return requestApi
    }
}