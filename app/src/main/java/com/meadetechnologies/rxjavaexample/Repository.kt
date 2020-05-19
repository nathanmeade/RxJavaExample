package com.meadetechnologies.rxjavaexample

import io.reactivex.rxjava3.core.Observable
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

    fun makeFutureQuery() : Future<Observable<ResponseBody>> {
        val executor = Executors.newSingleThreadExecutor()
        val myNetworkCallable = object : Callable<Observable<ResponseBody>> {
            override fun call(): Observable<ResponseBody> {
                return ServiceGenerator().getrequestApi().makeObservableQuery()
            }

        }

        val futureObservable = object : Future<Observable<ResponseBody>>{
            override fun isDone(): Boolean {
                return executor.isTerminated
            }

            override fun get(): Observable<ResponseBody> {
                return executor.submit(myNetworkCallable).get()
            }

            override fun get(timeout: Long, unit: TimeUnit?): Observable<ResponseBody> {
                return executor.submit(myNetworkCallable).get(timeout, unit)
            }

            override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
                if (mayInterruptIfRunning){
                    executor.shutdown()
                }
                return false
            }

            override fun isCancelled(): Boolean {
                return executor.isShutdown
            }

        }
        return futureObservable
    }
}