package com.estudos.leonardo.chucknorrisfacts.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.estudos.leonardo.chucknorrisfacts.R
import kotlinx.android.synthetic.main.activity_first_access.*

class FirstAccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_access)

        buttonComecar.setOnClickListener(){
            finish()
            startActivity(Intent(applicationContext, ListChuckNorrisFacts::class.java))
        }
    }

}
