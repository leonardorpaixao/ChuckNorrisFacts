package com.estudos.leonardo.chucknorrisfacts.view.random_fact

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenState
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenState.*
import kotlinx.android.synthetic.main.activity_random_chucknorris_fact.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class RandomFactsActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    private val mAdapterChuckNorris by instance<ChuckNorrisFactAdapter>()
    private val viewModel by instance<RandomFactsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_chucknorris_fact)
        setupRecyclerView()
        listenButtonRequestFact()
        viewModel.listenFact().observe(this, Observer { state -> handleState(state) })
    }

    private fun handleState(state: ScreenState<ChuckNorrisFacts>) {
        when (state) {
            Loading -> showLoadingView()
            is Result -> updateFact(state.result)
            is Error -> showErrorMessage(state.error)
        }
    }

    private fun updateFact(fact: ChuckNorrisFacts) {
        mAdapterChuckNorris.updateAdapterList(fact)
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
            Toast.LENGTH_SHORT
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

