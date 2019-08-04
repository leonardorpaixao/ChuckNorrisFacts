package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import kotlinx.android.synthetic.main.activity_random_chucknorris_fact.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RandomChuckNorrisFacts : AppCompatActivity() {


    lateinit var factsList: MutableList<ChuckNorrisFacts>
    private lateinit var mAdapterChuckNorris: ChuckNorrisFactAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_chucknorris_fact)

        //Carregamos a Recycler View com o ChuckNorrisListAdapter
        mAdapterChuckNorris = ChuckNorrisFactAdapter(this)
        myRecyclerView.adapter = mAdapterChuckNorris
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
                mAdapterChuckNorris.updateDataSet(fact)

            }, { e ->
                e.printStackTrace()
                Toast.makeText(
                    this, "Erro: Sua oração foi fraca, tente novamente",
                    Toast.LENGTH_LONG
                ).show()

            })
    }

}

