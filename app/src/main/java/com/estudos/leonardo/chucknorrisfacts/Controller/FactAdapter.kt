package com.estudos.leonardo.chucknorrisfacts.Controller

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.estudos.leonardo.chucknorrisfacts.Model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.R
import kotlinx.android.synthetic.main.fact_model.view.*

class ChuckNorrisFactsListAdapter(
    private val facts: List<ChuckNorrisFacts>,
    private val context: Context
) : RecyclerView.Adapter<ChuckNorrisFactsListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fact_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return facts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pessoa = facts[position]
        holder.bindView(pessoa)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindView(chuckNorrisFact: ChuckNorrisFacts) {
            val fact: TextView = itemView.textViewFact
            val category: TextView = itemView.textViewCategory

            fact.text = chuckNorrisFact.fact
            category.text = chuckNorrisFact.category!![0]
        }
    }

}