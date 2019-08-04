package com.estudos.leonardo.chucknorrisfacts.view

import android.os.Bundle
import android.preference.PreferenceManager
import com.estudos.leonardo.chucknorrisfacts.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.estudos.leonardo.chucknorrisfacts.view.dashboad.DashBoardActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume(){
        super.onResume()
        val prefs = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false)
        if (!previouslyStarted) {
            val edit = prefs.edit()
            edit.putBoolean(getString(R.string.pref_previously_started), java.lang.Boolean.TRUE)
            edit.apply()
            startActivity(Intent(applicationContext, FirstAccessActivity::class.java))
            finish()
        }
        else startActivity(Intent(applicationContext, DashBoardActivity::class.java))
        finish()
    }
}
