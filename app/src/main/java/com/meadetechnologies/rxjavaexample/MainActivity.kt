package com.meadetechnologies.rxjavaexample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.queryTextChanges
import com.meadetechnologies.rxjavaexample.models.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    val TAG = "RxJavaDebugLog"
    lateinit var disposables : CompositeDisposable
    var timeSinceLastRequest : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposables = CompositeDisposable()

        timeSinceLastRequest = System.currentTimeMillis()

        view.clicks().throttleFirst(4000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Unit>{
                override fun onComplete() {
                    TODO("Not yet implemented")
                }

                override fun onSubscribe(d: Disposable?) {
                    disposables.add(d)
                }

                override fun onNext(t: Unit?) {
                    Log.d(TAG, "onNext time since last clicked: ${System.currentTimeMillis() - timeSinceLastRequest}")
                    someMethod()
                }

                override fun onError(e: Throwable?) {
                    TODO("Not yet implemented")
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

    fun someMethod(){
        timeSinceLastRequest = System.currentTimeMillis()
        Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}