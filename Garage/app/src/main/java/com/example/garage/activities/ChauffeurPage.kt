package com.example.garage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.garage.LoginPref
import com.example.garage.R

class ChauffeurPage : AppCompatActivity() {

    private lateinit var tvName : TextView
    private lateinit var btnlogout : Button

    lateinit var session: LoginPref

    override fun finish() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chauffeur_page)

        session = LoginPref(this)

        tvName = findViewById(R.id.nametv)
        btnlogout = findViewById(R.id.buttonlogout)

        session.checkLogin()

        var user: HashMap<String,String> = session.getUserDetail()

        var name  = user.get(LoginPref.KEY_NAME)


        tvName.setText(name)

        btnlogout.setOnClickListener{
            session.logoutUser()
        }

    }

    fun Alerter(button: View){

        session.checkLogin()

        val aj = Intent(this, MissionList::class.java)
        aj.putExtra("okk",3)
        startActivity(aj)

    }

    fun MyMission(button:View){
        session.checkLogin()

        val aj = Intent(this, MissionList::class.java)
        startActivity(aj)
    }

    fun ConsulterAlert(view:View){
        session.checkLogin()

        val aj = Intent(this, AlertList::class.java)
        startActivity(aj)
    }

    fun ChangeMotdepasse(view: View){
        val aj = Intent(this,AjouterChauffeur::class.java)
        aj.putExtra("okk",6)
        startActivity(aj)
    }

}