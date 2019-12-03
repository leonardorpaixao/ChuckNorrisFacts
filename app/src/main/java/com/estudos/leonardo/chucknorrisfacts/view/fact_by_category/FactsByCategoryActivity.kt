package com.estudos.leonardo.chucknorrisfacts.view.fact_by_category

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenState
import kotlinx.android.synthetic.main.activity_chucknorris_fact_by_category.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class FactsByCategoryActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModel by instance<FactsByCategoryViewModel>()

    private var selectedCategory: String = ""
    private var listCategories = listOf<String>()
    private lateinit var factAdapter: ChuckNorrisFactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chucknorris_fact_by_category)
        viewModel.getCategoriesList()
        listenCategoriesList()
        listenFact()
        spinnerController()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        factAdapter = ChuckNorrisFactAdapter(this)
        recyclerViewfFactByCategory.adapter = factAdapter
        recyclerViewfFactByCategory.setHasFixedSize(true)
        recyclerViewfFactByCategory.layoutManager = LinearLayoutManager(this)

    }

    private fun spinnerController() {
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    applicationContext, "Por favor escolha uma opção",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                setSelectedCategory(listCategories.get(position))

            }
        }

        buttonRequest.setOnClickListener {
            if (selectedCategory.isNotBlank()) {
                viewModel.getFactByCategory(selectedCategory.toLowerCase())
            }
        }
    }

    private fun listenCategoriesList() {
        viewModel.listenCategories().observe(this, Observer { categoryScreenState ->
            handleScreenStateCategory(categoryScreenState)
        })
    }

    private fun listenFact() {
        viewModel.listenFact().observe(this, Observer { factScreenState ->
            handleScreenStateFact(factScreenState)
        })
    }

    private fun handleScreenStateFact(factScreenState: ScreenState<ChuckNorrisFacts>?) {
        when (factScreenState) {
            ScreenState.Loading -> {
                isShowingLoadingState(true)
            }
            is ScreenState.Error -> {
                Toast.makeText(
                    this, "Erro: Houve um erro na requisição. Tente novamente.",
                    Toast.LENGTH_LONG
                ).show()
            }
            is ScreenState.Result -> {
                updateFactAdapter(factScreenState.result)
            }
        }
    }

    private fun updateFactAdapter(chuckNorrisFact: ChuckNorrisFacts) {
        isShowingLoadingState(false)
        factAdapter.updateDataSet(chuckNorrisFact)
    }

    private fun handleScreenStateCategory(categoryScreenState: ScreenState<List<String>>) {
        when (categoryScreenState) {
            ScreenState.Loading -> {
                isShowingLoadingState(true)
            }
            is ScreenState.Error -> {
                Toast.makeText(
                    this, "Erro: Houve um erro na requisição. Tente novamente.",
                    Toast.LENGTH_LONG
                ).show()
            }
            is ScreenState.Result -> {
                listCategories = categoryScreenState.result
                updateCategoriesList(categoryScreenState.result)
            }
        }

    }

    private fun updateCategoriesList(listOfCategories: List<String>) {
        val newList = mutableListOf<String>()
            listOfCategories.map {
            newList.add(it.toUpperCase())
        }

        isShowingLoadingState(false)
        mySpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                newList
            )
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
    }

    private fun setSelectedCategory(selectedCategory: String) {
        this.selectedCategory = selectedCategory
    }

    private fun isShowingLoadingState(isShowingLoading: Boolean) {
        when (isShowingLoading) {
            true -> {
                factByCategoryContent.visibility = View.GONE
                factByCategorieLoadingState.visibility = View.VISIBLE
            }
            false -> {
                factByCategoryContent.visibility = View.VISIBLE
                factByCategorieLoadingState.visibility = View.GONE
            }
        }
    }
}

