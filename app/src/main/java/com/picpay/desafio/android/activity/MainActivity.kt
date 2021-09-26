package com.picpay.desafio.android.activity

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.BR
import com.picpay.desafio.android.R
import com.picpay.desafio.android.adapter.UserListAdapter
import com.picpay.desafio.android.common.activity.BaseAcitivity
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.di.mainModule
import com.picpay.desafio.android.domain.ResultWrapper
import com.picpay.desafio.android.domain.user.GetUserUseCase
import com.picpay.desafio.android.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class MainActivity : BaseAcitivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main), CoroutineScope {

    override val bindingVariable: Int = BR.mainViewModel
    override val viewModel: MainViewModel by viewModel()

    override val modules: List<Module> = listOf(mainModule)

    private val adapter: UserListAdapter by lazy {
        UserListAdapter()
    }

    override fun onResume() {
        super.onResume()
        setupView()
    }

    private fun setupView() {
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
    }
}
