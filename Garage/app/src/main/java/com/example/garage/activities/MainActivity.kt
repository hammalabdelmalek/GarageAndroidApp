package com.example.garage.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.View
import com.example.garage.*

class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var session: LoginPref

    override fun finish() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        session = LoginPref(this)

        if(session.isloggedin()){
            var i : Intent = Intent(applicationContext, AdminPage::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }

    }

    fun adminConnection(button:View){

        val aj = Intent(this, Admin_connection::class.java)
        startActivity(aj)

    }

    fun chauffeurConnection(button: View) {

        val aj = Intent(this, ChauffeurConnexion::class.java)
        startActivity(aj)

    }


}