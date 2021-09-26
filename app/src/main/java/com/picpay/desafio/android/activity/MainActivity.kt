package com.picpay.desafio.android.activity

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.adapter.UserListAdapter
import com.picpay.desafio.android.domain.ResultWrapper
import com.picpay.desafio.android.domain.user.GetUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(R.layout.activity_main), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Main + job

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val userUseCase: GetUserUseCase by inject()

    override fun onResume() {
        super.onResume()
        setupView()
        executeCall()
    }

    private fun setupView() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun executeCall() {
        launch {
            userUseCase(Unit).onEach { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ResultWrapper.DismissLoading -> {
                        progressBar.visibility = View.GONE
                    }
                    is ResultWrapper.Success -> {
                        progressBar.visibility = View.GONE

                        adapter.users = result.value
                    }
                    is ResultWrapper.Error -> {
                        val message = getString(R.string.error)

                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE

                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }.launchIn(CoroutineScope(coroutineContext))
        }
    }
}
