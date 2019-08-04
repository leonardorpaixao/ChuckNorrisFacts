package com.estudos.leonardo.chucknorrisfacts.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.estudos.leonardo.chucknorrisfacts.R
import com.estudos.leonardo.chucknorrisfacts.view.dashboad.DashBoardActivity
import kotlinx.android.synthetic.main.activity_first_access.*

class FirstAccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_access)

        buttonComecar.setOnClickListener(){
            finish()
            startActivity(Intent(applicationContext, DashBoardActivity::class.java))
        }
    }

}
