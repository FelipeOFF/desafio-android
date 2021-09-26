package com.picpay.desafio.android.common.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.picpay.desafio.android.common.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseAcitivity<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes val layout: Int
) : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    abstract val bindingVariable: Int?

    abstract val viewModel: VM?

    val binding: VB? by lazy {
        setupBinding()
    }

    private fun setupBinding(): VB? {
        val binding = DataBindingUtil.setContentView<VB>(this, layout)
        bindingVariable?.let { bindingVariable ->
            binding?.setVariable(bindingVariable, viewModel)
        }
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}