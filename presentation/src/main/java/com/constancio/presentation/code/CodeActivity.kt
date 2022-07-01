package com.constancio.presentation.code

import android.os.Bundle
import android.widget.Toast
import com.constancio.presentation.R
import com.constancio.presentation.databinding.ActivityCodeBinding
import com.constancio.presentation.ui.base.BaseActivity
import com.constancio.presentation.ui.extensions.observeNotNull
import org.koin.android.viewmodel.ext.android.viewModel

class CodeActivity : BaseActivity<ActivityCodeBinding>() {

    val viewModel by viewModel<CodeViewModel>()

    override fun getLayoutRes(): Int = R.layout.activity_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        observeData()
    }

    /**
     * Load path and times fetched quantity every time on resume is called.
     */
    override fun onResume() {
        super.onResume()
        viewModel.loadPath()
        viewModel.countTimesFetched()
    }

    /**
     * Observe error and display a Toast.
     */
    private fun observeData() {
        viewModel.error.observeNotNull(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }
    }
}