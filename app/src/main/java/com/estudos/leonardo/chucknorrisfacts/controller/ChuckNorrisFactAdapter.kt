package com.estudos.leonardo.chucknorrisfacts.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts


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

    fun updateAdapterList(fact: ChuckNorrisFacts) {
        val newList = mutableListOf<ChuckNorrisFacts>()
        newList.add(fact)
        newList.addAll(this.facts)
        this.facts = newList
        notifyDataSetChanged()
    }

    fun resetList() {
        facts = mutableListOf()
        notifyDataSetChanged()
    }

}