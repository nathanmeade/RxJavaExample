package com.meadetechnologies.rxjavaexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.clicks
import com.meadetechnologies.rxjavaexample.models.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    val TAG = "RxJavaDebugLog"
    lateinit var disposables : CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposables = CompositeDisposable()

        view.clicks().map(object : Function<Unit, Int>{
            override fun apply(t: Unit?): Int {
                return 1
            }

        })
            .buffer(4, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Int>>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable?) {
                    disposables.add(d)
                }

                override fun onNext(t: List<Int>?) {
                    Log.d(TAG, "Number of times button was clicked in 4 second span: ${t!!.size}")
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

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}

