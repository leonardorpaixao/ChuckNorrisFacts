package com.estudos.leonardo.chucknorrisfacts.view.dashboad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenSelected.*
import com.estudos.leonardo.chucknorrisfacts.view.ChuckNorrisFactByWord
import com.estudos.leonardo.chucknorrisfacts.view.ChuckNorrisFactsByCategory
import com.estudos.leonardo.chucknorrisfacts.view.RandomChuckNorrisFacts
import kotlinx.android.synthetic.main.activity_dash_board.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DashBoardActivity : AppCompatActivity() {

    private var listCategories = mutableListOf<String>()
    private val viewModel = DashBoardViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        getListOfCategories()
        listenButtons()
        listenToNavigate()


    }

    fun listenToNavigate() {
        viewModel.listenScreenSelected().observe(this, Observer {screenSelected ->
            when (screenSelected) {
                FactByRandom -> {
                    startActivity(Intent(applicationContext, RandomChuckNorrisFacts::class.java))
                }
                FactByCategory -> {
                    intent = Intent(applicationContext, ChuckNorrisFactsByCategory::class.java)
                    intent.putExtra("listCategoriesFromDashBoardActivity", ArrayList(listCategories))
                    startActivity(Intent(intent))
                }
                FactByWord -> {
                    startActivity(Intent(applicationContext, ChuckNorrisFactByWord::class.java))
                }
            }
        })

    }

    //Atualiza lista de categorias dos fatos.
    fun getListOfCategories() {
        val api = ChuckNorrisFactsApi()
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

    fun listenButtons(){
        buttonRandomJoke.setOnClickListener {
            viewModel.setScreen(FactByRandom)
            Log.d("Track", "buttonRandomJoke")
        }
        buttonJokeByCategory.setOnClickListener {
            viewModel.setScreen(FactByCategory)
            Log.d("Track", "buttonJokeByCategory")
        }
        buttonJokeByWord.setOnClickListener {
            viewModel.setScreen(FactByWord)
            Log.d("Track", "buttonJokeByWord")
        }

    }
}
