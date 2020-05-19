package com.meadetechnologies.rxjavaexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.meadetechnologies.rxjavaexample.models.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    val TAG = "RxJavaDebugLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val task = Task("Walk the dog", false, 3)

        val taskObservable = Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(object : Predicate<Long>{
                    override fun test(t: Long?): Boolean {
                        Log.d(TAG, "Long: $t thread: ${Thread.currentThread().name}")
                        return t!! <= 5
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : Observer<Long>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onNext(t: Long?) {
                Log.d(TAG, "onNext: $t")
            }

            override fun onError(e: Throwable?) {

            }

        })
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
