package com.estudos.leonardo.chucknorrisfacts.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import kotlinx.android.synthetic.main.fact_model.view.*

class ChuckNorrisFactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bindView(chuckNorrisFact: ChuckNorrisFacts, context: Context) {

        //define o tamanho do texto inflado de acordo com o número de char do fato.
        itemView.textViewFact.textSize = textSizeDefiner(chuckNorrisFact.fact)

        val fact: TextView = itemView.textViewFact
        val category: TextView = itemView.textViewCategory
        val buttonShare = itemView.buttonShare
        fact.text = chuckNorrisFact.fact
        category.text = chuckNorrisFact.category!![0]

        //Compartilha fato de Chuck Norris
        buttonShare.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.setType("text/palin")
            myIntent.putExtra(Intent.EXTRA_SUBJECT, "ChuckNorrisFacts APP")
            val shareText = chuckNorrisFact.fact
            val strShareMessage = "\nChuck Norris Fact: \n\n $shareText"
            myIntent.putExtra(Intent.EXTRA_TEXT, strShareMessage)

            ContextCompat.startActivity(
                context,
                Intent.createChooser(myIntent, "Compartilhar Fato do Chuck Norris com:"),
                Bundle()
            )

        }

    }
    //retorna um float de acordo com o tamanho da string passada.
    private fun textSizeDefiner(fact: String): Float {
        return if (fact.length < 80) {
            24.0F
        } else 18.0F
    }
}