package com.estudos.leonardo.chucknorrisfacts.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.estudos.leonardo.chucknorrisfacts.Controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.R
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var listView : ListView
    lateinit var movieAdapter : ArrayAdapter<String>
    var movies = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        listView = ListView(this)
        super.onCreate(savedInstanceState)
        setContentView(listView)
        movieAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, movies)
        listView.adapter = movieAdapter

        val api = ChuckNorrisFactsApi()
        api.loadFact()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({ fact ->
                movies.add("${fact.id} -- ${fact.fact}")

            }, { e ->
                e.printStackTrace()
            },{
                movieAdapter.notifyDataSetChanged()
            })
/*
        api.loadMoviesFull()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    movie ->
                movies.add("${movie.title} -- ${movie.episodeId}\n ${movie.characters.toString() }")
            }, {
                    e ->
                e.printStackTrace()
            },{
                movieAdapter?.notifyDataSetChanged()
            })*/
    }
}
