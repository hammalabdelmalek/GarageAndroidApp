package com.example.garage.Views

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
import com.example.garage.activities.AjouterMission
import com.example.garage.activities.MapsActivity
import com.example.garage.data.Mission
import com.example.garage.data.User
import com.example.garage.data.Vehicule
import com.example.garage.modeles.MissionModel
import com.example.garage.modeles.ParkingModel
import com.example.garage.modeles.UserModel
import com.example.garage.modeles.VehiculeModel
import kotlinx.android.synthetic.main.activity_alert_view.*
import kotlinx.android.synthetic.main.activity_mission_view.*

class MissionView : AppCompatActivity() {

    private lateinit var vehiculeModel: VehiculeModel
    private lateinit var userModel: UserModel
    private lateinit var missionModel: MissionModel
    private lateinit var parkingModel: ParkingModel

    private lateinit var titre : TextView
    private lateinit var tv_address1 : TextView
    private lateinit var tv_address2 : TextView
    private lateinit var tv_chauffeur : TextView
    private lateinit var tv_kilo : TextView
    private lateinit var tv_date : TextView
    private lateinit var etat : TextView
    private lateinit var tv_vehicule : TextView
    private lateinit var tv_consomotion : TextView
    private lateinit var et_note : EditText

    private lateinit var button : Button
    private lateinit var buttonUpdate :Button
    private lateinit var buttonStart :Button
    private lateinit var buttonMap: Button
    private lateinit var buttonEnd : Button
    private lateinit var buttonNote : Button

    private lateinit var M : Mission
    private lateinit var v : Vehicule

    private var id = 0
    lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_view)

        this.setTitle("Mission")

        session = LoginPref(this)

        session.checkLogin()

        titre = findViewById(R.id.titre)
        tv_address1 = findViewById(R.id.tv_address1)
        tv_address2 = findViewById(R.id.tv_address2)
        tv_chauffeur = findViewById(R.id.tv_chauffeur)
        etat = findViewById(R.id.etat)
        tv_vehicule = findViewById(R.id.tv_vehicule)
        et_note = findViewById(R.id.et_note)
        tv_kilo = findViewById(R.id.tv_kilometrage)
        tv_date = findViewById(R.id.tv_debut)
        button = findViewById(R.id.Delete)
        buttonUpdate = findViewById(R.id.Update)
        tv_consomotion = findViewById(R.id.tv_consomation)
        buttonStart = findViewById(R.id.Start)
        buttonMap = findViewById(R.id.Map)
        buttonEnd = findViewById(R.id.End)
        buttonNote = findViewById(R.id.Noter)

        vehiculeModel = ViewModelProvider(this).get(VehiculeModel::class.java)
        userModel = ViewModelProvider(this).get(UserModel::class.java)
        missionModel = ViewModelProvider(this).get(MissionModel::class.java)
        parkingModel = ViewModelProvider(this).get(ParkingModel::class.java)

        id = intent.getIntExtra("id",0)

        M = missionModel.getMission(id.toLong())!!


        var C : User? = userModel.getUser(M.chauffaurId)

        var v = vehiculeModel.getVehicul(M.vehiculeId)

        titre.text = M.title
        tv_address1.text = M.adreesseDebut
        tv_kilo.text = M.kilometrage.toString()
        tv_debut.text = M.dateDebut
        tv_address2.text = M.adresseFin
        etat.text = etatToString(M.etat)

        if(C != null){
            tv_chauffeur.text = C.name
        }else{
            tv_chauffeur.text = "Chauffeur supprimer ("+M.chauffaur+")"
        }

        if(v != null){
            tv_vehicule.text = v.marque + " " + v.modele
            tv_consomotion.text = ((v.consomation * M.kilometrage)/100).toString()+"L"
        }else{
            tv_vehicule.text = "Un vehicule supprimé"

        }

        if(session.getType() != 1){
            button.isVisible = false
            buttonUpdate.isVisible = false

            if(M.etat > 0){
                buttonStart.isVisible = false
                if(M.etat <= 1){
                    buttonEnd.isVisible = true
                }
            }
        }else{

            if(M.etat == 2 && !M.note){
               buttonNote.isVisible = true
            }

            if(M.etat > 0){
                button.isVisible = false
                buttonUpdate.isVisible = false
            }
            buttonStart.isVisible= false
        }


        button.setOnClickListener {

            android.app.AlertDialog.Builder(this)
                .setMessage("Confirmez-vous la suppression ?")
                .setPositiveButton("Oui") { _, _ ->

                    missionModel.deleteMission(id.toLong())
                    vehiculeModel.changeEtat(1,M.vehiculeId)
                    val aj = Intent(this, AdminPage::class.java)
                    startActivity(aj)

                }
                .setNegativeButton("Annuler"){_,_->

                }
                .create()
                .show()
        }

        buttonUpdate.setOnClickListener {

            val aj = Intent(this,AjouterMission::class.java)
            aj.putExtra("okk",5)
            aj.putExtra("id",id)
            startActivity(aj)

        }

        buttonStart.setOnClickListener {
            missionModel.changeEtat(1,M.id)
            val aj = Intent(this,MissionView::class.java)
            aj.putExtra("id",M.id)
            startActivity(aj)
        }

        buttonEnd.setOnClickListener {
            missionModel.changeEtat(2,M.id)
            vehiculeModel.changeEtat(1,M.vehiculeId)
            val v = vehiculeModel.getVehicul(M.vehiculeId)
            val u = userModel.getUser(M.chauffaurId)
            if(u!=null){
                userModel.setKilometrage(u.kilometrage+M.kilometrage,u.id)
            }
            if(v!=null){
                vehiculeModel.setKilometrage(v.kilometrage+M.kilometrage,v.id)
            }
            val aj = Intent(this,MissionView::class.java)
            aj.putExtra("id",M.id)
            startActivity(aj)
        }

        buttonNote.setOnClickListener {

            if(!et_note.isVisible){
                et_note.isVisible = true
            }else{
                var note = et_note.text.toString().trim()
                if(note.isEmpty()){
                    Toast.makeText(this,"vous devez remplir le champs note",Toast.LENGTH_SHORT).show()
                }else{
                    if(!isNumber(note)){
                        Toast.makeText(this,"Donner une note valide entre 1 et 5",Toast.LENGTH_SHORT).show()
                    }else{
                        var n = note.toInt()
                        if(n<1 || n > 5){
                            Toast.makeText(this,"Donner une note valide entre 1 et 5",Toast.LENGTH_SHORT).show()
                        }else{
                            //ici en ajoute la note
                            if(C != null){
                                var f = C.note
                                if(f == 0F){
                                    userModel.noter(n.toFloat(),C.id)
                                    userModel.setNumroMission(1,C.id)
                                    missionModel.noter(M.id)
                                }else{
                                    f = f * C.nbrMission.toFloat()
                                    f = f + n.toFloat()
                                    f = f / (C.nbrMission + 1).toFloat()
                                    userModel.noter(f,C.id)
                                    userModel.setNumroMission(C.nbrMission+1,C.id)
                                    missionModel.noter(M.id)
                                }
                                Toast.makeText(this,"Mission bien Noté",Toast.LENGTH_SHORT).show()
                                buttonNote.isVisible = false
                                et_note.isVisible = false
                            }
                        }
                    }
                }
            }

        }

    }

    fun isNumber(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

    fun etatToString(i : Int):String{
        if(i == 0){
            return "Pas encore commencé"
        }
        if(i == 1){
            return "En cours"
        }
        if(i == 2){
            return "Terminé"
        }
        return ""
    }

    fun Maps(view: View){
        val aj = Intent(this,MapsActivity::class.java)
        startActivity(aj)
    }
}