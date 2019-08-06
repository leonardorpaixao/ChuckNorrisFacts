package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import kotlinx.android.synthetic.main.activity_chucknorris_fact_by_category.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FactsByCategoryActivity : AppCompatActivity() {

    var listCategories = listOf<String>()
    var selectedItem: String = ""
    private lateinit var myAdapterChuckNorris: ChuckNorrisFactAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chucknorris_fact_by_category)

        //Carregando categorias atualizadas no SpinnerAdapter
        listCategories = intent.getStringArrayListExtra("listCategoriesFromDashBoardActivity") as List<String>
        updateAdapter(listCategories)

        //Definindo recyclerView adapter
        myAdapterChuckNorris = ChuckNorrisFactAdapter(this)
        recyclerViewfFactByCategory.adapter = myAdapterChuckNorris
        recyclerViewfFactByCategory.setHasFixedSize(true)
        recyclerViewfFactByCategory.layoutManager = LinearLayoutManager(this)


        //definindo comportamentos para quando itens do spinner forem selecionados
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    applicationContext, "Por favor escolha uma opção",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = listCategories.get(position)
            }
        }

        buttonRequest.setOnClickListener {
            loadFacts(this.selectedItem)
        }
    }

    //Função para solicitar um fato de ChuckNorris de acordo com uma categoria.
    private fun loadFacts(selectedItem: String) {
        val api = ChuckNorrisFactsApi()
        api.requestFactByCategory(selectedItem.toLowerCase())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ fact ->
                myAdapterChuckNorris.updateDataSet(fact)

            }, { e ->
                e.printStackTrace()
                Toast.makeText(
                    this, "Erro: Houve um erro na requisição. Tente novamente.",
                    Toast.LENGTH_LONG
                ).show()

            })

    }

    //Atualizar o Spinner Adapter a partir da lista de categorias obtidas.
    fun updateAdapter(listOfCategories: List<String>) {
        mySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOfCategories)
    }
}

