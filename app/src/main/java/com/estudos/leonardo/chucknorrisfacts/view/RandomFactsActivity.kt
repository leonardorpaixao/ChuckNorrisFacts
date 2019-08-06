package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenState
import kotlinx.android.synthetic.main.activity_random_chucknorris_fact.*

class RandomFactsActivity : AppCompatActivity() {

    private val mAdapterChuckNorris: ChuckNorrisFactAdapter by lazy { ChuckNorrisFactAdapter(applicationContext) }
    private val viewModel: RandomFactsViewModel by lazy { RandomFactsViewModel(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_chucknorris_fact)
        setupRecyclerView()
        listenButtonRequestFact()
        viewModel.listenFact().observe(this, Observer { state -> handleState(state) })
    }

    private fun handleState(state: ScreenState<ChuckNorrisFacts>) {
        when (state) {
            ScreenState.Loading -> showLoadingView()
            is ScreenState.Result -> updateFact(state.result)
            is ScreenState.Failed -> showFailedMessage()
            is ScreenState.Error -> showErrorMessage(state.error)
        }
    }

    private fun updateFact(fact: ChuckNorrisFacts) {
        mAdapterChuckNorris.updateDataSet(fact)
    }

    private fun showFailedMessage() {
        Toast.makeText(
            applicationContext, "Erro: Sua oração foi fraca, tente novamente",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showErrorMessage(result: Throwable) {
        Toast.makeText(
            applicationContext, "Erro: Sua oração foi fraca, tente novamente",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoadingView() {
        Toast.makeText(
            applicationContext, "Estamos captando sua oração",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun listenButtonRequestFact() {
        buttonRequestFact.setOnClickListener {
            viewModel.getRandomFact()
        }
    }

    private fun setupRecyclerView() {
        myRecyclerView.adapter = mAdapterChuckNorris
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}

