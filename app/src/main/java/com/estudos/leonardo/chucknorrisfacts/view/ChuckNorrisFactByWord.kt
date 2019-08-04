package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import kotlinx.android.synthetic.main.activity_chucknorris_fact_by_word.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ChuckNorrisFactByWord : AppCompatActivity() {
    private lateinit var myChuckNorrisFactAdapter: ChuckNorrisFactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chucknorris_fact_by_word)

        myChuckNorrisFactAdapter = ChuckNorrisFactAdapter(this)
        recyclerViewfFactByWord.adapter = myChuckNorrisFactAdapter
        recyclerViewfFactByWord.setHasFixedSize(true)
        recyclerViewfFactByWord.layoutManager = LinearLayoutManager(this)


        buttonSearchByWord.setOnClickListener() {
            if (editTextSearch.text.toString() == "") {
                Toast.makeText(
                    this, "Error: Enter a word to continue.",
                    Toast.LENGTH_LONG
                ).show()

            } else
                loadFacts(editTextSearch.text.toString())
        }
    }


private fun loadFacts(selectedItem: String) {
    myChuckNorrisFactAdapter.resetList()

    val api = ChuckNorrisFactsApi()
    api.requestFactByWord(selectedItem.toLowerCase())!!
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ fact ->
            myChuckNorrisFactAdapter.updateDataSet(fact)

        }, { e ->
            e.printStackTrace()
            Toast.makeText(
                this, "Erro: Houve um erro na requisição. Tente novamente.",
                Toast.LENGTH_LONG
            ).show()

        }, {

            if (myChuckNorrisFactAdapter.itemCount == 0) {
                Toast.makeText(
                    this, "No results found. Try another word",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
}
}

