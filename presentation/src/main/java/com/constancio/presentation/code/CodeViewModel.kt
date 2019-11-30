package com.constancio.presentation.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.constancio.domain.exception.DefaultException
import com.constancio.domain.interaction.CodeUseCase
import com.constancio.presentation.ui.base.BaseViewModel
import com.constancio.presentation.utils.SingleLiveData

class CodeViewModel(
    private val codeUseCase: CodeUseCase
) : BaseViewModel() {

    private val _code = MutableLiveData<String>()
    val responseCode: LiveData<String> get() = _code

    val error = SingleLiveData<DefaultException>()

    fun loadPath() {
        subscribeCompletable(
            observable = codeUseCase.getNextPath()
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            complete = {
                //todo
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
                _code.postValue(it.responseCode)
            },
            error = { error.postValue(it) }
        )
    }

    fun onClickFetchCode() {
        fetchResponseCode()
    }
}