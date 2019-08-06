package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import kotlinx.android.synthetic.main.activity_random_chucknorris_fact.*

class RandomFactsActivity : AppCompatActivity() {

    private val mAdapterChuckNorris: ChuckNorrisFactAdapter by lazy { ChuckNorrisFactAdapter(applicationContext) }
    private val viewModel: RandomFactsViewModel by lazy { RandomFactsViewModel(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_chucknorris_fact)
        setupRecyclerView()
        listenButtonRequestFact()
        updateAdapter()
    }

    private fun updateAdapter() {
        viewModel.listenFact().observe(this, Observer {
            mAdapterChuckNorris.updateDataSet(it)
        })
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

