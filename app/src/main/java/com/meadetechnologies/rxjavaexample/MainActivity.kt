package com.meadetechnologies.rxjavaexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.meadetechnologies.rxjavaexample.models.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.IOException
import java.util.concurrent.ExecutionException


class MainActivity : AppCompatActivity() {

    val TAG = "RxJavaDebugLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        val taskObservable = Observable
                .fromIterable(createTasksList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : Observer<Task>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onNext(t: Task?) {
                Log.d(TAG, "onNext: : " + t!!.description)
            }

            override fun onError(e: Throwable?) {

            }

        })*/

        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        try {
            mainViewModel.makeFutureQuery().get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseBody>{
                    override fun onComplete() {
                        Log.d(TAG, "onComplete: called.")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        Log.d(TAG, "onSubscribe: called.")
                    }

                    override fun onNext(t: ResponseBody?) {
                        Log.d(TAG, "onNext: got the response from server!")
                        try {
                            Log.d(TAG, "onNext: " + t!!.string())
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG, "onError: ", e)
                    }

                })
        } catch (e : ExecutionException) {
            e.printStackTrace()
        } catch (e : InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun createTasksList() : List<Task>{
        val tasks = ArrayList<Task>()
        tasks.add(Task("Take out the trash", true, 3))
        tasks.add(Task("Walk the dog", false, 2))
        tasks.add(Task("Make my bed", true, 1))
        tasks.add(Task("Unload the dishwasher", false, 0))
        tasks.add(Task("Make dinner", true, 5))
        return tasks
    }
}

