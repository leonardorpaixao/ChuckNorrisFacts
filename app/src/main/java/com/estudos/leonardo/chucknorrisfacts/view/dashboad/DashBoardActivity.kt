package com.estudos.leonardo.chucknorrisfacts.view.dashboad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.data.FactsInfraStructure
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenSelected.*
import com.estudos.leonardo.chucknorrisfacts.view.fact_by_category.FactsByCategoryActivity
import com.estudos.leonardo.chucknorrisfacts.view.fact_by_word.FactsByWordActivity
import com.estudos.leonardo.chucknorrisfacts.view.random_fact.RandomFactsActivity
import kotlinx.android.synthetic.main.activity_dash_board.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DashBoardActivity : AppCompatActivity() {

    private var listCategories = mutableListOf<String>()
    private val viewModel = DashBoardViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        listenButtons()
        listenToNavigate()


    }

    fun listenToNavigate() {
        viewModel.listenScreenSelected().observe(this, Observer {screenSelected ->
            when (screenSelected) {
                FactByRandom -> {
                    startActivity(Intent(applicationContext, RandomFactsActivity::class.java))
                }
                FactByCategory -> {
                    intent = Intent(applicationContext, FactsByCategoryActivity::class.java)
                    intent.putExtra("listCategoriesFromDashBoardActivity", ArrayList(listCategories))
                    startActivity(Intent(intent))
                }
                FactByWord -> {
                    startActivity(Intent(applicationContext, FactsByWordActivity::class.java))
                }
            }
        })

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
