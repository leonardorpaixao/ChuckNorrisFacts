package com.estudos.leonardo.chucknorrisfacts.controller


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.estudos.leonardo.chucknorrisfacts.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.R


class ChuckNorrisFactAdapter(
    private val context: Context
) : RecyclerView.Adapter<ChuckNorrisFactHolder>() {

    private var facts: MutableList<ChuckNorrisFacts> = mutableListOf<ChuckNorrisFacts>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ChuckNorrisFactHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fact_model, parent, false)
        return ChuckNorrisFactHolder(view)
    }

    override fun getItemCount(): Int {
        return facts.size
    }

    override fun onBindViewHolder(holderChuckNorris: ChuckNorrisFactHolder, position: Int) {
        val fact = facts[position]
        holderChuckNorris.bindView(fact, context)
    }

    //Adiciona um novo item a RecyclerView, e à atualiza.
    fun updateDataSet(fact: ChuckNorrisFacts) {
        this.facts.add(fact)
        notifyDataSetChanged()
    }
    //Zera a lista do adapter e atualiza a Recycler View
    fun resetList(){
        facts = mutableListOf<ChuckNorrisFacts>()
        notifyDataSetChanged()
    }

}