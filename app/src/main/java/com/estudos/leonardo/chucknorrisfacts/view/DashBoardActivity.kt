package com.estudos.leonardo.chucknorrisfacts.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.estudos.leonardo.chucknorrisfacts.R
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        buttonRandomJoke.setOnClickListener {
            startActivity(Intent(applicationContext, RandomChuckNorrisFacts::class.java))
        }
        buttonJokeByCategory.setOnClickListener{
            startActivity(Intent(applicationContext, ChuckNorrisFactByCategory::class.java))
        }
        buttonJokeByWord.setOnClickListener{
            startActivity(Intent(applicationContext, ChuckNorrisFactByWord::class.java))
        }

    }
}
