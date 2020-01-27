package com.estudos.leonardo.chucknorrisfacts.view.fact_by_word

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
import kotlinx.android.synthetic.main.activity_chucknorris_fact_by_word.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class FactsByWordActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModel by instance<FactByWordViewModel>()
    private lateinit var myChuckNorrisFactAdapter: ChuckNorrisFactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chucknorris_fact_by_word)
        initializeRecyclerView()
        listenSearchButton()
        listenResult()
    }

    private fun listenResult() {
        viewModel.listenResult().observe(this, Observer { state -> handleState(state) })
    }

    private fun handleState(screenState: ScreenState<ChuckNorrisFacts>) {
        when (screenState) {
            Loading -> {
            }
            is Result -> populate(screenState.result)
            is Error -> showErrorMessage()
        }
    }

    private fun populate(result: ChuckNorrisFacts) {
        myChuckNorrisFactAdapter.updateAdapterList(result)
        verifyIfIsNoResult()

    }

    private fun listenSearchButton() {
        editTextSearch.requestFocus()
        buttonSearchByWord.setOnClickListener {
            if (editTextSearch.text.toString() == "") {
                Toast.makeText(
                    this, "Error: Enter a word to continue.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                myChuckNorrisFactAdapter.resetList()
                requireFactByWord(editTextSearch.text.toString())
            }
        }
    }

    private fun initializeRecyclerView() {
        myChuckNorrisFactAdapter = ChuckNorrisFactAdapter(this)
        recyclerViewfFactByWord.adapter = myChuckNorrisFactAdapter
        recyclerViewfFactByWord.setHasFixedSize(true)
        recyclerViewfFactByWord.layoutManager = LinearLayoutManager(this)
    }


    private fun requireFactByWord(selectedItem: String) {
        viewModel.getFactByWord(selectedItem.toLowerCase())

    }

    private fun showErrorMessage() {
        Toast.makeText(
            this, "Erro: Houve um erro na requisição. Tente novamente.",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun verifyIfIsNoResult() {
        if (myChuckNorrisFactAdapter.itemCount == 0) {
            Toast.makeText(
                this, "No results found. Try another word",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

