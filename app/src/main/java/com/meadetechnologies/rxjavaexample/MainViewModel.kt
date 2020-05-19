package com.meadetechnologies.rxjavaexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import java.util.concurrent.Future


class MainViewModel : ViewModel {

    lateinit var repository: Repository

    constructor(){
        repository = Repository.getinstance()
    }

    fun makeQuery(): LiveData<ResponseBody> {
        return repository.makeReactiveQuery()
    }
}