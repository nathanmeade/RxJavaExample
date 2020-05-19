package com.meadetechnologies.rxjavaexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.meadetechnologies.rxjavaexample.models.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    val TAG = "RxJavaDebugLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskObservable = Observable
                .fromArray(Task("Take out the trash", true, 3),
                    Task("Walk the dog", false, 2),
                    Task("Make my bed", true, 1),
                    Task("Unload the dishwasher", false, 0),
                    Task("Make dinner", true, 5))
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

