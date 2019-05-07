package com.estudos.leonardo.chucknorrisfacts.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.estudos.leonardo.chucknorrisfacts.model.ChuckNorrisFacts
import kotlinx.android.synthetic.main.fact_model.view.*

class ChuckNorrisFactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bindView(chuckNorrisFact: ChuckNorrisFacts, context: Context) {

        itemView.textViewFact.textSize = textSizeDefiner(chuckNorrisFact.fact)

        val fact: TextView = itemView.textViewFact
        val category: TextView = itemView.textViewCategory
        val buttonShare = itemView.buttonShare
        fact.text = chuckNorrisFact.fact
        category.text = chuckNorrisFact.category!![0]

        buttonShare.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.setType("text/palin")
            myIntent.putExtra(Intent.EXTRA_SUBJECT, "ChuckNorrisFacts APP")

            val shareText = chuckNorrisFact.fact

            val strShareMessage = "\nChuck Norris Fact: \n\n $shareText"
            //val screenshotUri = Uri.parse("android.resource://com.estudos.leonardo.chucknorrisfacts/drawable/captura_de_tela.png")
            //myIntent.type = "image/png"
            //myIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            myIntent.putExtra(Intent.EXTRA_TEXT, strShareMessage)

            ContextCompat.startActivity(
                context,
                Intent.createChooser(myIntent, "Compartilhar Fato do Chuck Norris com:"),
                Bundle()
            )

        }

    }

    private fun textSizeDefiner(fact: String): Float {
        return if (fact.length < 80) {
            24.0F
        } else 18.0F
    }
}