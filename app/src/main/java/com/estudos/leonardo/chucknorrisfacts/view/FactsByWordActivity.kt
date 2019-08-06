package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import kotlinx.android.synthetic.main.activity_chucknorris_fact_by_word.*

class FactsByWordActivity : AppCompatActivity() {
    private val myChuckNorrisFactAdapter: ChuckNorrisFactAdapter by lazy { ChuckNorrisFactAdapter(applicationContext) }
    private val viewModel: FactsByWordViewModel by lazy { FactsByWordViewModel(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chucknorris_fact_by_word)
        setupRecycleView()
        listenRequest()
        updateAdapter()
        viewModel.listenFact()

    }

    private fun listenRequest() {
        buttonSearchByWord.setOnClickListener {
            if (editTextSearch.text.toString() == "") {
                Toast.makeText(
                    this, "Error: Enter with a word to continue.",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                myChuckNorrisFactAdapter.resetList()
                viewModel.getFactByWord(editTextSearch.text.toString().toLowerCase())

            }
        }
    }

    private fun setupRecycleView() {
        recyclerViewFactByWord.adapter = myChuckNorrisFactAdapter
        recyclerViewFactByWord.setHasFixedSize(true)
        recyclerViewFactByWord.layoutManager = LinearLayoutManager(this)
    }

    private fun updateAdapter() {
        viewModel.listenFact().observe(this, Observer {
            if (it == null) {
            } else myChuckNorrisFactAdapter.updateDataSet(it)
        })
    }

    fun verifyIfExistFact() {
        /*if (myChuckNorrisFactAdapter.itemCount == 0) {*/
        Toast.makeText(
            this, "No results found. Try another word",
            Toast.LENGTH_LONG
        ).show()

    }
}


