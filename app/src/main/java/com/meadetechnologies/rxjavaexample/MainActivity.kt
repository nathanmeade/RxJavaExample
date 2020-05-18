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


class MainActivity : AppCompatActivity() {

    val TAG = "RxJavaDebugLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val task = Task("Walk the dog", false, 3)

        val taskObservable = Observable
                .create(object : ObservableOnSubscribe<Task>{
                    override fun subscribe(emitter: ObservableEmitter<Task>?) {
                        if (!emitter!!.isDisposed){
                            emitter.onNext(task)
                            emitter.onComplete()
                        }
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : Observer<Task>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onNext(t: Task?) {
                Log.d(TAG, "onNext: " + t!!.description)
            }

            override fun onError(e: Throwable?) {

            }

        })
    }
}
