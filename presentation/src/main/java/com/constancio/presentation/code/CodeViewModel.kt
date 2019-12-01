package com.constancio.presentation.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.constancio.domain.exception.DefaultException
import com.constancio.domain.interaction.CodeUseCase
import com.constancio.domain.model.Code
import com.constancio.presentation.ui.base.BaseViewModel
import com.constancio.presentation.utils.SingleLiveData

class CodeViewModel(
    private val codeUseCase: CodeUseCase
) : BaseViewModel() {

    private val _code = MutableLiveData<Code>()
    val code: LiveData<Code> get() = _code

    private val _timesFetched = MutableLiveData<Int>()
    val timesFetched: LiveData<Int> get() = _timesFetched

    val error = SingleLiveData<DefaultException>()

    fun loadPath() {
        subscribeCompletable(
            observable = codeUseCase.getNextPath()
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            complete = {
                // success Log info
            },
            error = { error.postValue(it) }
        )
    }

    fun fetchResponseCode() {
        subscribeSingle(
            observable = codeUseCase.getResponseCode()
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            success = {
                _code.postValue(it)
                countTimesFetched()
            },
            error = { error.postValue(it) }
        )
    }

    fun countTimesFetched() {
        subscribeSingle(
            observable = codeUseCase.getTimesFetched()
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            success = {
                _timesFetched.postValue(it)
            },
            error = { error.postValue(it) }
        )
    }

    fun onClickFetchCode() {
        fetchResponseCode()
    }
}