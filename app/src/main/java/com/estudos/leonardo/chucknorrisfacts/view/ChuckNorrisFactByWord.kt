package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import kotlinx.android.synthetic.main.activity_chucknorris_fact_by_category.*
import kotlinx.android.synthetic.main.activity_chucknorris_fact_by_word.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ChuckNorrisFactByWord : AppCompatActivity() {
    private lateinit var myAdapterChuckNorris: ChuckNorrisFactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chucknorris_fact_by_word)

        myAdapterChuckNorris = ChuckNorrisFactAdapter(this)
        recyclerViewfFactByWord.adapter = myAdapterChuckNorris
        recyclerViewfFactByWord.setHasFixedSize(true)
        recyclerViewfFactByWord.layoutManager = LinearLayoutManager(this)


        buttonSearchByWord.setOnClickListener() {
            if (editTextSearch.text.toString() == "") {
               textViewError.text = getString(R.string.msgEmptySearch)

            } else {
                textViewError.text = ""
                Snackbar.make(
                    factByWordConstraintLayout, "System: Please, wait a moment",
                    Snackbar.LENGTH_LONG
                ).show()
                loadFacts(editTextSearch.text.toString())
            }

        }



    }

    private fun loadFacts(selectedItem: String) {
        val api = ChuckNorrisFactsApi()
        api.requestFactByWord(selectedItem.toLowerCase())!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ fact ->
                myAdapterChuckNorris.updateDataSet(fact)

            }, { e ->
                e.printStackTrace()
                Snackbar.make(
                    myConstraintLayout, "Erro: Houve um erro na requisição. Tente novamente.",
                    Snackbar.LENGTH_LONG
                ).show()

            })
    }
}

