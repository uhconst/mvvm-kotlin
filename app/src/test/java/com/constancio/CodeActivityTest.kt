package com.constancio

import androidx.lifecycle.Observer
import com.constancio.presentation.code.CodeActivity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

/**
 * CodeActivityTest is a KoinTest with RobolectricTest runner
 *
 * KoinTest help inject Koin components from actual runtime
 */
@RunWith(RobolectricTestRunner::class)
class CodeActivityTest : KoinTest {

    private val observerTimesFetchedMock: Observer<Int> = mock()

    private lateinit var controller: ActivityController<CodeActivity>

    /**
     * Setup Robolectric before starting.
     */
    @Before
    fun `Setup test`() {
        controller = Robolectric.buildActivity(CodeActivity::class.java).create()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    /**
     * After test is complete:
     * Stop Koin.
     * Reset RxPlugins.
     * Destroy controller .
     */
    @After
    fun `Finish test`() {
        stopKoin()
        RxJavaPlugins.reset()
        controller.destroy()
    }

    @Test
    fun `Test countTimesFetched() when activity is resumed`() {
        //Prepare
        val activity = controller.get()
        activity.viewModel.timesFetched.observeForever(observerTimesFetchedMock)

        //Action
        controller.resume()

        //Test
        verify(observerTimesFetchedMock).onChanged(any())
    }
}