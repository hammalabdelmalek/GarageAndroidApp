package com.example.garage.Views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.activities.AdminPage
import com.example.garage.activities.AjouteVehicule
import com.example.garage.data.Mission
import com.example.garage.data.User
import com.example.garage.modeles.*

class VehiculeView : AppCompatActivity() {


    private lateinit var vehiculeModel: VehiculeModel
    private lateinit var userModel: UserModel
    private lateinit var missionModel: MissionModel
    private lateinit var notifModel: NotifModel
    private lateinit var parkingModel: ParkingModel

    private lateinit var titre : TextView
    private lateinit var annee : TextView
    private lateinit var typeTv :TextView
    private lateinit var nbrTv : TextView
    private lateinit var consoTv :TextView
    private lateinit var kilometrageTv : TextView
    private lateinit var occupe1 : TextView
    private lateinit var occupe2 : TextView
    private lateinit var etat : TextView
    private lateinit var dateLimit: TextView

    private lateinit var button: Button
    private lateinit var buttonUpdate : Button

    private lateinit var M : Mission
    private lateinit var C : User

    private var id = 0
    lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicule_view)

        this.setTitle("Vehicule")

        session = LoginPref(this)

        session.checkLogin()

        session.checkAdmin()

        titre = findViewById(R.id.titre)
        annee = findViewById(R.id.annee)
        occupe1 = findViewById(R.id.occupe)
        occupe2 = findViewById(R.id.occupe2)
        typeTv = findViewById(R.id.type)
        nbrTv = findViewById(R.id.nbr_place)
        consoTv = findViewById(R.id.consomation)
        etat = findViewById(R.id.etat)
        kilometrageTv = findViewById(R.id.kilometrage)
        dateLimit = findViewById(R.id.dateLimit)
        buttonUpdate = findViewById(R.id.Update)

        vehiculeModel = ViewModelProvider(this).get(VehiculeModel::class.java)
        userModel = ViewModelProvider(this).get(UserModel::class.java)
        missionModel = ViewModelProvider(this).get(MissionModel::class.java)
        parkingModel = ViewModelProvider(this).get(ParkingModel::class.java)
        notifModel = ViewModelProvider(this).get(NotifModel::class.java)

        id = intent.getIntExtra("id",0)

        var lv = vehiculeModel.getVehicule(id.toLong())

        var v = lv[0]

        var lm = missionModel.allMission()

        if(!v.etat){

            for(m in lm){
                if (m.vehiculeId == v.id){
                    M = m
                    break
                }
            }

            C = userModel.getUser(M.chauffaurId)!!

        }

        titre.text = v.marque+" "+v.modele
        typeTv.text = v.titre
        consoTv.text = v.consomation.toString() +"L/100Km"
        nbrTv.text = v.nbrPlace.toString()
        annee.text = v.anneeSerculation
        kilometrageTv.text = v.kilometrage.toString()
        dateLimit.text = v.controleTechnique
        if(v.etat){
            etat.text = "Libre"
            occupe1.isVisible = false
            occupe2.isVisible = false
        }else{
            etat.text = "Occupé"
            occupe1.text = "Le vehicule est occupé pour une mission  de :"+M.title
            occupe2.text = "Le vehicule est occupé par le chauffeur : "+C.name
        }

        button =findViewById(R.id.Delete)

        button.setOnClickListener {

            if(!v.etat){
                Toast.makeText(this,"Vous ne pouvez pas supprimer un vehicule en cours d'utilisation",Toast.LENGTH_SHORT).show()
            }else{

                android.app.AlertDialog.Builder(this)
                    .setMessage("Confirmez-vous la suppression ?")
                    .setPositiveButton("Oui") { _, _ ->

                        vehiculeModel.Delete(id.toLong())
                        parkingModel.changeEtat(1,v.parkingId)
                        notifModel.deleteNotifV(id.toLong())
                        val aj = Intent(this, AdminPage::class.java)
                        startActivity(aj)
                    }
                    .setNegativeButton("Annuler"){_,_->

                    }
                    .create()
                    .show()

            }
        }
     buttonUpdate.setOnClickListener {
         val aj = Intent(this,AjouteVehicule::class.java)
         aj.putExtra("okk",5)
         aj.putExtra("id",id)
         startActivity(aj)
     }

    }

}