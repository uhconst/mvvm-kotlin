package com.constancio.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.constancio.domain.exception.DefaultException
import com.constancio.domain.interaction.CodeUseCase
import com.constancio.domain.model.Code
import com.constancio.presentation.code.CodeViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class CodeViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    companion object {
        private val MOCK_CODE = Code(
            "xxxxx-xxxxx",
            "zzzzz-zzzzz"
        )

        private const val MOCK_TIME_FETCHED = 1

        private val MOCK_EXCEPETION = DefaultException("Mock error message")
    }

    private val codeUseCaseMock = mock<CodeUseCase>()

    private val observerCodeMock: Observer<Code> = mock()

    private val observerTimesFetchedMock: Observer<Int> = mock()

    private val observerErrorMock: Observer<DefaultException> = mock()

    lateinit var viewModel: CodeViewModel

    @Before
    fun `Setup test`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = CodeViewModel(
            codeUseCaseMock
        )
    }

    @Test
    fun `Test fetchResponseCode() when success`() {
        //Prepare
        val subjectDelayResponseCode = PublishSubject.create<Code>()
        val subjectDelayTimesFetched = PublishSubject.create<Int>()

        Mockito.`when`(codeUseCaseMock.getResponseCode())
            .thenReturn(Single.just(MOCK_CODE).delaySubscription(subjectDelayResponseCode))

        Mockito.`when`(codeUseCaseMock.getTimesFetched())
            .thenReturn(Single.just(MOCK_TIME_FETCHED).delaySubscription(subjectDelayTimesFetched))

        viewModel.code.observeForever(observerCodeMock)

        //Action
        viewModel.fetchResponseCode()

        //Test
        `Test showLoading`(subjectDelayResponseCode)
        verify(observerCodeMock).onChanged(MOCK_CODE)
    }

    @Test
    fun `Test fetchResponseCode() when error`() {
        //Prepare
        Mockito.`when`(codeUseCaseMock.getResponseCode())
            .thenReturn(Single.error(MOCK_EXCEPETION))

        viewModel.error.observeForever(observerErrorMock)

        //Action
        viewModel.fetchResponseCode()

        //Test
        verify(observerErrorMock).onChanged(MOCK_EXCEPETION)
    }

    @Test
    fun `Test countTimesFetched() when success`() {
        //Prepare
        val subjectDelayTimesFetched = PublishSubject.create<Int>()

        Mockito.`when`(codeUseCaseMock.getTimesFetched())
            .thenReturn(Single.just(MOCK_TIME_FETCHED).delaySubscription(subjectDelayTimesFetched))

        viewModel.timesFetched.observeForever(observerTimesFetchedMock)

        //Action
        viewModel.countTimesFetched()

        //Test
        `Test showLoading`(subjectDelayTimesFetched)
        verify(observerTimesFetchedMock).onChanged(MOCK_TIME_FETCHED)
    }

    @Test
    fun `Test countTimesFetched() when error`() {
        //Prepare
        Mockito.`when`(codeUseCaseMock.getTimesFetched())
            .thenReturn(Single.error(MOCK_EXCEPETION))

        viewModel.error.observeForever(observerErrorMock)

        //Action
        viewModel.countTimesFetched()

        //Test
        verify(observerErrorMock).onChanged(MOCK_EXCEPETION)
    }

    @Test
    fun `Test loadPath() when error`() {
        //Prepare
        Mockito.`when`(codeUseCaseMock.getNextPath())
            .thenReturn(Completable.error(MOCK_EXCEPETION))

        viewModel.error.observeForever(observerErrorMock)

        //Action
        viewModel.loadPath()

        //Test
        verify(observerErrorMock).onChanged(MOCK_EXCEPETION)
    }

    private fun <T> `Test showLoading`(subjectDelay: PublishSubject<T>) {
        MatcherAssert.assertThat(viewModel.showLoading.get(), CoreMatchers.`is`(true))

        subjectDelay.onComplete()

        MatcherAssert.assertThat(viewModel.showLoading.get(), CoreMatchers.`is`(false))
    }
}

