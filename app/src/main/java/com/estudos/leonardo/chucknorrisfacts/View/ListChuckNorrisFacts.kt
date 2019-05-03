package com.estudos.leonardo.chucknorrisfacts.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.ArrayAdapter
import android.widget.ListView
import com.estudos.leonardo.chucknorrisfacts.Controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.Controller.ChuckNorrisFactsListAdapter
import com.estudos.leonardo.chucknorrisfacts.Model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.R
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.*

class ListChuckNorrisFacts : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var movieAdapter: ArrayAdapter<String>
    var movies = mutableListOf<String>()
    lateinit var factsList: MutableList<ChuckNorrisFacts>


    override fun onCreate(savedInstanceState: Bundle?) {
        listView = ListView(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        movieAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, movies
        )
        listView.adapter = movieAdapter


        //val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        factsList = mutableListOf<ChuckNorrisFacts>()
        factsList = loadFacts()

    }

    fun loadFacts(): MutableList<ChuckNorrisFacts> {
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        val api = ChuckNorrisFactsApi()
        var myMutableList: MutableList<ChuckNorrisFacts> = mutableListOf<ChuckNorrisFacts>()
        var factObject: ChuckNorrisFacts
        for (i in 0..19) {
            api.loadFact()
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ fact ->
                    myMutableList.add(fact)
                    myRecyclerView.adapter = ChuckNorrisFactsListAdapter(myMutableList, applicationContext)

                }, { e ->
                    e.printStackTrace()
                })
        }
        return myMutableList
    }
}
