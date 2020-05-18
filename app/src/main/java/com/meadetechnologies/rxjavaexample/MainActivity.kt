package com.meadetechnologies.rxjavaexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.meadetechnologies.rxjavaexample.models.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    val TAG = "RxJavaDebugLog"

    val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskObservable = Observable.fromIterable(createTasksList())
                .subscribeOn(Schedulers.io())
                .filter { t ->
                    Log.d(TAG, "test: ${Thread.currentThread().name}")
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException){
                        e.printStackTrace()
                    }
                    t?.isComplete!!
                }
                .observeOn(AndroidSchedulers.mainThread())



        taskObservable.subscribe(object : Observer<Task> {
            override fun onComplete() {
                Log.d(TAG, "onComplete called.")
            }

            override fun onSubscribe(d: Disposable?) {
                Log.d(TAG, "onSubscribe called")
                disposables.add(d)
            }

            override fun onNext(t: Task?) {
                Log.d(TAG, "onNext: ${Thread.currentThread().name}")
                if (t != null) {
                    Log.d(TAG, "onNext: ${t.description}")
                }
            }

            override fun onError(e: Throwable?) {
                Log.e(TAG, "onError: ", e)
            }

        })

        disposables.add(taskObservable.subscribe(object : Consumer<Task>{
            override fun accept(t: Task?) {

            }
        }))
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

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
