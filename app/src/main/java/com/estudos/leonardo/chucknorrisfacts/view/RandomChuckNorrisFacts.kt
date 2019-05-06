package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsListAdapter
import com.estudos.leonardo.chucknorrisfacts.model.ChuckNorrisFacts
import kotlinx.android.synthetic.main.activity_random_chucknorris_fact.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RandomChuckNorrisFacts : AppCompatActivity() {


    lateinit var factsList: MutableList<ChuckNorrisFacts>
    private lateinit var mAdapter: ChuckNorrisFactsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_chucknorris_fact)

        //Carregamos a Recycler View com o ChuckNorrisListAdapter
        mAdapter = ChuckNorrisFactsListAdapter(this)
        myRecyclerView.adapter = mAdapter
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        //Definição de comportamento do botão "buttonRequestFact"
        buttonRequestFact.setOnClickListener {
            loadFacts()
        }

    /*button.setOnClickListener{
        val api = ChuckNorrisFactsApi()
        api.requestCategories()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                it ->
                textViewCategories.text = it.categories.toString()
                }){e ->
                e.printStackTrace()
            }

    }*/
    }

    /*Instanciar um observable através do retorno da nossa função requestFact, definida em nossa api ChuckNorrisFactsApi,
      e em seguida enviar a instancia do objeto de ChuckNorrisFact para o adapter, através da função updateDataSet */
    fun loadFacts() {
        val api = ChuckNorrisFactsApi()
        api.requestFact()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ fact ->
                mAdapter.updateDataSet(fact)

            }, { e ->
                e.printStackTrace()
                Snackbar.make(
                    myConstraintLayout, "Erro: Sua oração foi fraca, tente novamente",
                    Snackbar.LENGTH_LONG
                ).show()

            })
    }

}

