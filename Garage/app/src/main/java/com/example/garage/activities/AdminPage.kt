package com.example.garage.activities

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.data.Notif
import com.example.garage.modeles.AlertModel
import com.example.garage.modeles.NotifModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AdminPage : AppCompatActivity() {
    private lateinit var tvName : TextView
    private lateinit var btnlogout :Button
    private lateinit var alertModel: AlertModel
    private lateinit var notifModel: NotifModel
    private lateinit var view: View

    lateinit var session: LoginPref

    override fun finish() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)



        session = LoginPref(this)

        tvName = findViewById(R.id.nametv)
        btnlogout = findViewById(R.id.buttonlogout)

        session.checkLogin()
        session.checkAdmin()

        alertModel = ViewModelProvider(this).get(AlertModel::class.java)
        notifModel = ViewModelProvider(this).get(NotifModel::class.java)
        Notifs()
        view = findViewById(R.id.newAlert)
        
        if(alertModel.newMessages()){
            view.isVisible = true
        }


        var user: HashMap<String,String> = session.getUserDetail()

        var name  = user.get(LoginPref.KEY_NAME)
        var email = user.get(LoginPref.KEY_EMAIL)

        tvName.setText(name)

        btnlogout.setOnClickListener{
            session.logoutUser()
        }
    }

    fun AjouterChauffeur(button:View){

        session.checkLogin()

        val aj = Intent(this, AjouterChauffeur::class.java)
        startActivity(aj)

    }

    fun AttribueMission(button: View){

        session.checkLogin()

        val aj = Intent(this, AjouterMission::class.java)
        startActivity(aj)

    }

    fun ConsulterChauffeur(button: View){
        session.checkLogin()

        val aj = Intent(this, ChauffeurList::class.java)
        startActivity(aj)

    }

    fun ConsulterVehicule(button: View){
        session.checkLogin()

        val aj = Intent(this, VehiculeList::class.java)
        startActivity(aj)

    }

    fun ConsulterParking(button: View){
        session.checkLogin()

        val aj = Intent(this, ParkingList::class.java)
        startActivity(aj)

    }

    fun AjouterVehicule(button: View){
        session.checkLogin()

        val aj = Intent(this, AjouteVehicule::class.java)
        startActivity(aj)

    }

    fun ConsulterMission(button: View){

        session.checkAdmin()

        val aj = Intent(this, MissionList::class.java)
        startActivity(aj)

    }

    fun ConsulterAlerts(view:View){
        session.checkAdmin()

        val aj = Intent(this, AlertList::class.java)
        startActivity(aj)
    }

    fun ConsulterNotif(view: View){
        session.checkAdmin()

        val aj = Intent(this, NotifList::class.java)
        startActivity(aj)
    }

    fun Notifs(){

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val a = currentDate.split(" ")
        val date = a[0].split("/")
        var ls = emptyList<Notif>()
        ls = notifModel.allNotif()
        for(n in ls){
            val dn = n.date
            var an = dn.split("/")
            if(an[2].toInt() == date[2].toInt()){
                if(an[1].toInt() >= date[1].toInt()-1){
                    notifModel.changeEtat(n.id)
                }
            }else{
                if(an[2].toInt() == date[2].toInt()-1){
                    if(date[1].toInt() == 12 && an[1].toInt() == 1){
                        notifModel.changeEtat(n.id)
                    }
                }
            }
        }

    }



}