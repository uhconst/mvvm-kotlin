package com.constancio.presentation.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

/**
 * Base implementation for all activities in the application.
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B

    protected val compositeDisposable = CompositeDisposable()

    abstract fun getLayoutRes(): Int

    /**
     * Called when the activity is starting.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutRes())

        /** Enable MutableLiveData to be updated on the UI */
        binding.lifecycleOwner = this
    }

    /** The final call you receive before your activity is destroyed. */
    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}