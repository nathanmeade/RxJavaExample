package com.meadetechnologies.rxjavaexample

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface RequestApi {
    @GET("todos/1")
    fun makeObservableQuery() : Observable<ResponseBody>
}