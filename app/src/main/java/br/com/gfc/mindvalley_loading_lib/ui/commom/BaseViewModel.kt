package br.com.mercafacil.app_mercafacil.view.commom

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

open class BaseViewModel : ViewModel() {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun cancelOperations() {
        compositeDisposable.clear()
        compositeDisposable.dispose()

    }

    fun addDisposable(observable: DisposableObserver<*>) {
        compositeDisposable.add(observable)
    }


}