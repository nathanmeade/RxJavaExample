package com.meadetechnologies.rxjavaexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class Repository {



    companion object {
        private var instance : Repository? = null

        fun getinstance() : Repository{
            if (instance == null){
                instance = Repository()
            }
            return instance!!
        }
    }

    fun makeReactiveQuery() : LiveData<ResponseBody>{
        return LiveDataReactiveStreams.fromPublisher(ServiceGenerator.getrequestApi()
            .makeQuery()
            .subscribeOn(Schedulers.io()))
    }
}