package com.constancio.presentation.code

import android.os.Bundle
import com.constancio.presentation.R
import com.constancio.presentation.databinding.ActivityCodeBinding
import com.constancio.presentation.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class CodeActivity : BaseActivity<ActivityCodeBinding>() {

    val viewModel by viewModel<CodeViewModel>()

    override fun getLayoutRes(): Int = R.layout.activity_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }

    /**
     * Load path every time on resume is called
     */
    override fun onResume() {
        super.onResume()
        viewModel.loadPath()
    }
}