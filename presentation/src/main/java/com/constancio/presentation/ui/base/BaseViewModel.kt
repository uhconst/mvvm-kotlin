package com.constancio.presentation.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.constancio.domain.exception.DefaultException
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base implementation for all view models in the application.
 */
abstract class BaseViewModel : ViewModel() {

    val showLoading = ObservableBoolean()

    private val compositeDisposable = CompositeDisposable()

    protected fun <T> subscribeSingle(
        observable: Single<T>,
        success: ((T) -> Unit)? = null,
        error: ((DefaultException) -> Unit)? = null
    ): Disposable {
        val disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                success,
                {
                    when (it) {
                        is DefaultException -> error?.invoke(it)
                        else -> error?.invoke(DefaultException())
                    }
                }
            )

        compositeDisposable.add(disposable)

        return disposable
    }

    protected fun subscribeCompletable(
        observable: Completable,
        complete: (() -> Unit)? = null,
        error: ((DefaultException) -> Unit)? = null
    ): Disposable {
        val disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                complete,
                {
                    when (it) {
                        is DefaultException -> error?.invoke(it)
                        else -> error?.invoke(DefaultException())
                    }
                }
            )

        compositeDisposable.add(disposable)

        return disposable
    }

    /**
     * Dispose the resource
     */
    protected fun dispose(disposable: Disposable?) {
        disposable?.let { compositeDisposable.remove(it) }
    }

    /**
     * Clear the ongoing `Disposable` operations when this `ViewModel` is no longer used
     * and will be destroyed.
     */
    override fun onCleared() {
        compositeDisposable.clear()
    }
}