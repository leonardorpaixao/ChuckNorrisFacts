package com.estudos.leonardo.chucknorrisfacts.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.Controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.Controller.ChuckNorrisFactsListAdapter
import com.estudos.leonardo.chucknorrisfacts.Model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.R
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ListChuckNorrisFacts : AppCompatActivity() {


    lateinit var factsList: MutableList<ChuckNorrisFacts>
    private lateinit var mAdapter: ChuckNorrisFactsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Carregamos a Recycler View com o ChuckNorrisListAdapter
        mAdapter = ChuckNorrisFactsListAdapter(this)
        myRecyclerView.adapter = mAdapter

        //Definição de comportamento do botão "buttonRequestFact"
        buttonRequestFact.setOnClickListener() {
            loadFacts()
        }



    }

    /*Instanciar um observable através do retorno da nossa função requestFact, definida em nossa api ChuckNorrisFactsApi,
      e em seguida enviar a instancia do objeto de ChuckNorrisFact para o adapter, através da função updateDataSet */
    fun loadFacts() {
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        val api = ChuckNorrisFactsApi()
        api.requestFact()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ fact ->
                mAdapter.updateDataSet(fact)

            }, { e ->
                e.printStackTrace()
            })
    }

}

