package com.estudos.leonardo.chucknorrisfacts.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import kotlinx.android.synthetic.main.activity_dash_board.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DashBoardActivity : AppCompatActivity() {

    private var listCategories = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        getListOfCategories()

        //Direciona para tela "Random Fact"
        buttonRandomJoke.setOnClickListener {
            startActivity(Intent(applicationContext, RandomChuckNorrisFacts::class.java))
        }
        //Direciona para tela "Fact By Category"
        buttonJokeByCategory.setOnClickListener {
            intent = Intent(applicationContext, ChuckNorrisFactsByCategory::class.java)
            intent.putExtra("listCategoriesFromDashBoardActivity", ArrayList(listCategories))
            startActivity(Intent(intent))
        }
        //Direciona para tela "Fact By Word"
        buttonJokeByWord.setOnClickListener {
            startActivity(Intent(applicationContext, ChuckNorrisFactByWord::class.java))
        }
    }

    //Atualiza lista de categorias dos fatos.
    fun getListOfCategories() {
        val api = ChuckNorrisFactsApi()
        val list = listOf<String>()
        api.requestCategories()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ categories ->

                categories.categories.map {
                    updateList(it.toUpperCase())

                }
            }, { e ->
                e.printStackTrace()
            }, {

            })
    }

    //Adiciona item a mutableList
    fun updateList(category: String) {
        this.listCategories.add(category)
    }
}
