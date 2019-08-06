package com.estudos.leonardo.chucknorrisfacts.controller


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFact
import com.estudos.leonardo.chucknorrisfacts.R


class ChuckNorrisFactAdapter(
    private val context: Context
) : RecyclerView.Adapter<ChuckNorrisFactHolder>() {

    private var facts: MutableList<ChuckNorrisFact> = mutableListOf<ChuckNorrisFact>()

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
    fun updateDataSet(fact: ChuckNorrisFact) {
        this.facts.add(fact)
        notifyDataSetChanged()
    }
    //Zera a lista do adapter e atualiza a Recycler View
    fun resetList(){
        facts = mutableListOf<ChuckNorrisFact>()
        notifyDataSetChanged()
    }

}