package com.meadetechnologies.rxjavaexample

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import java.util.concurrent.Future


class MainViewModel : ViewModel {

    lateinit var repository: Repository

    constructor(){
        repository = Repository.getinstance()
    }

    fun makeFutureQuery(): Future<Observable<ResponseBody>> {
        return repository.makeFutureQuery()
    }
}