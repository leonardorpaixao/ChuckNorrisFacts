package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsListAdapter
import kotlinx.android.synthetic.main.activity_chucknorris_fact_by_category.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ChuckNorrisFactByCategory : AppCompatActivity() {
    var listCategories = listOf<String>()
    var selectedItem: String = ""
    private lateinit var myAdapter: ChuckNorrisFactsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chucknorris_fact_by_category)

        val api = ChuckNorrisFactsApi()
        api.requestCategories()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ categories ->

                listCategories = categories.categories.map {
                    it.toUpperCase()
                }
                updateAdapter(listCategories)
            }) { e ->
                e.printStackTrace()
            }
        myAdapter = ChuckNorrisFactsListAdapter(this)
        recyclerViewfFactByCategory.adapter = myAdapter
        recyclerViewfFactByCategory.setHasFixedSize(true)
        recyclerViewfFactByCategory.layoutManager = LinearLayoutManager(this)


        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Snackbar.make(
                    myConstraintLayout, "Por favor escolha uma opção",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = listCategories.get(position)
            }
        }


        buttonRequest.setOnClickListener {
            loadFacts()
        }


    }

    private fun loadFacts() {
        val api = ChuckNorrisFactsApi()
        api.requestFactByCategory(selectedItem.toLowerCase())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ fact ->
                myAdapter.updateDataSet(fact)

            }, { e ->
                e.printStackTrace()
                Snackbar.make(
                    myConstraintLayout, "Erro: Houve um erro na requisição. Tente novamente.",
                    Snackbar.LENGTH_LONG
                ).show()

            })

    }


fun updateAdapter(list: List<String>) {
    mySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
}


}
/*fun loadCategories(){
    val api = ChuckNorrisFactsApi()
    api.requestCategories()
        .subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe({
            this.myList = it.categories
        }){e ->
            e.printStackTrace()
        }
}*/
