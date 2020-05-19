package com.meadetechnologies.rxjavaexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.meadetechnologies.rxjavaexample.models.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    val TAG = "RxJavaDebugLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extractDescriptionFunction = object : Function<Task, String>{
            override fun apply(t: Task?): String {
                Log.d(TAG, "Current thread: ${Thread.currentThread().name}")
                return t!!.description
            }

        }

        val taskObservable = Observable
            .fromIterable(createTasksList())
            .subscribeOn(Schedulers.io())
            .map(extractDescriptionFunction)
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : Observer<String>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onNext(t: String?) {
                Log.d(TAG, "onNext: description: $t")
            }

            override fun onError(e: Throwable?) {

            }

        })
    }

    private fun createTasksList() : List<Task>{
        val tasks = ArrayList<Task>()
        tasks.add(Task("Take out the trash", true, 3))
        tasks.add(Task("Walk the dog", true, 2))
        tasks.add(Task("Make my bed", true, 1))
        tasks.add(Task("Make dinner", true, 5))
        tasks.add(Task("Unload the dishwasher", false, 0))
        tasks.add(Task("Make dinner", true, 5))
        tasks.add(Task("Make dinner2", true, 5))
        return tasks
    }
}

