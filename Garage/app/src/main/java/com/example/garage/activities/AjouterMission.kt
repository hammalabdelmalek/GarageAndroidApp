package com.example.garage.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.room.Update
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.modeles.MissionModel
import com.example.garage.modeles.VehiculeModel
import kotlinx.android.synthetic.main.activity_ajouter_chauffeur.*
import java.sql.BatchUpdateException

class AjouterMission : AppCompatActivity() {

    private lateinit var missionModel:MissionModel
    private lateinit var vehiculeModel:VehiculeModel
    private lateinit var titreEt : EditText
    private lateinit var adresseFinEt : EditText
    private lateinit var adresseDebutEt: EditText
    private lateinit var dateDebutEt :EditText
    private lateinit var dateFinEt: EditText
    private lateinit var et_kilometrage : EditText
    private lateinit var title : TextView

    private lateinit var buttonAjout : Button
    private lateinit var buttonUpdate : Button

    var i = 0

    lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_mission)

        session = LoginPref(this)

        missionModel = ViewModelProvider(this).get(MissionModel::class.java)
        vehiculeModel = ViewModelProvider(this).get(VehiculeModel::class.java)

        session.checkAdmin()

        i = intent.getIntExtra("okk", 0)

        titreEt = findViewById(R.id.Title)
        adresseFinEt = findViewById(R.id.AdreeseArrivé)
        adresseDebutEt = findViewById(R.id.AdreeseDepart)
        dateDebutEt = findViewById(R.id.dateDebut)
        dateFinEt = findViewById(R.id.dateLimit)
        title = findViewById(R.id.title)
        et_kilometrage = findViewById(R.id.kilometrage)
        buttonAjout = findViewById(R.id.Ajout)
        buttonUpdate = findViewById(R.id.Update)

        Update()

        buttonUpdate.setOnClickListener {
            UpdateMission()
        }
    }

    @SuppressLint("ResourceAsColor")
    fun AjouteMission(button: View){

        session.checkAdmin()

        var ls = vehiculeModel.allFreeVehicule()

        if(ls.size == 0){
            var t = Toast.makeText(this, "Pas de vehicule libre pour la mission ils sont tous occupé", Toast.LENGTH_SHORT)
            val toastView: View? = t.view
            if (toastView != null) {
                toastView.setBackgroundColor(android.R.color.holo_red_dark)
            }
            t.show()
            return
        }

        val titre = titreEt.text.toString().trim()
        val adresseFin = adresseFinEt.text.toString().trim()
        val adresseDebut = adresseDebutEt.text.toString().trim()
        val dateDebut = dateDebutEt.text.toString().trim()
        val dateLimit = dateFinEt.text.toString().trim()
        val kilometrage = et_kilometrage.text.toString().trim()


        if(titre.isEmpty() || adresseFin.isEmpty() ||adresseDebut.isEmpty() || dateDebut.isEmpty() || dateLimit.isEmpty() || kilometrage.isEmpty()){
            Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }else{
            if(!testDate(dateDebut) || !testDate(dateLimit)){
                Toast.makeText(this, "Forme de date invalide forme juste : JJ/MM/AAAA", Toast.LENGTH_SHORT).show()
                return
            }
            if(!isFloat(kilometrage)){
                Toast.makeText(this, "Donner un kilometrage juste ", Toast.LENGTH_SHORT).show()
                return
            }
            if(kilometrage.toFloat() > 1000 || kilometrage.toFloat() < 1){
                Toast.makeText(this, "seuille non respecté ", Toast.LENGTH_SHORT).show()
                return
            }

            val aj = Intent(this, VehiculeList::class.java)
            aj.putExtra("titre",titre)
            aj.putExtra("adresseFin",adresseFin)
            aj.putExtra("adresseDebut",adresseDebut)
            aj.putExtra("dateDebut",dateDebut)
            aj.putExtra("dateLimit",dateLimit)
            aj.putExtra("kilo",kilometrage.toFloat())
            aj.putExtra("okk",3)
            startActivity(aj)
        }

    }

    fun Update(){

        if(i == 5){
         val id = intent.getIntExtra("id",0)
         val mission = missionModel.getMission(id.toLong())
         if(mission != null){
             titreEt.setText(mission.title)
             adresseFinEt.setText(mission.adresseFin)
             adresseDebutEt.setText(mission.adreesseDebut)
             dateFinEt.setText(mission.dateLimit)
             dateDebutEt.setText(mission.dateDebut)
             title.text = "Modification de mission"
             et_kilometrage.setText(mission.kilometrage.toString())
             buttonUpdate.isVisible = true
             buttonAjout.isVisible = false
         }
        }

    }

    fun UpdateMission(){

        session.checkAdmin()


        val titre = titreEt.text.toString().trim()
        val adresseFin = adresseFinEt.text.toString().trim()
        val adresseDebut = adresseDebutEt.text.toString().trim()
        val dateDebut = dateDebutEt.text.toString().trim()
        val dateLimit = dateFinEt.text.toString().trim()
        val kilometrage = et_kilometrage.text.toString().trim()


        if(titre.isEmpty() || adresseFin.isEmpty() ||adresseDebut.isEmpty() || dateDebut.isEmpty() || dateLimit.isEmpty() || kilometrage.isEmpty()){
            Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }else{

            if(!testDate(dateDebut) || !testDate(dateLimit)){
                Toast.makeText(this, "Forme de date invalide forme juste : JJ/MM/AAAA", Toast.LENGTH_SHORT).show()
                return
            }

            if(!isFloat(kilometrage)){
                Toast.makeText(this, "Donner un kilometrage juste ", Toast.LENGTH_SHORT).show()
                return
            }

            if(kilometrage.toFloat() > 1000 || kilometrage.toFloat() < 1){
                Toast.makeText(this, "seuille non respecté ", Toast.LENGTH_SHORT).show()
                return
            }

            val id = intent.getIntExtra("id",0)
            missionModel.updateMission(titre,adresseDebut,adresseFin,dateDebut,dateLimit,id.toLong())

            val aj = Intent(this, AdminPage::class.java)
            startActivity(aj)
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

    fun isFloat(s: String): Boolean {
        return try {
            s.toFloat()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }


    fun testDate(s:String):Boolean{
        var a = s.split("/").toTypedArray()
        if(a.size != 3){
            return false
        }
        for(elem in a){
            if(!isNumber(elem)){
                return false
            }
        }
        if(a[0].toInt() > 31 || a[0].toInt() < 1){
            return false
        }
        if(a[1].toInt() > 12 || a[1].toInt() < 1){
            return false
        }
        if(a[2].toInt() < 1 ){
            return false
        }

        return true
    }

}