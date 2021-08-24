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
import com.example.garage.activities.AlertList
import com.example.garage.activities.ChauffeurPage
import com.example.garage.modeles.AlertModel
import com.example.garage.modeles.MissionModel
import com.example.garage.modeles.UserModel

class AlertView : AppCompatActivity() {

    private lateinit var userModel: UserModel
    private lateinit var alertModel: AlertModel
    private lateinit var missionModel: MissionModel

    private lateinit var tv_type : TextView
    private lateinit var tv_name: TextView
    private lateinit var tv_desc : TextView
    private lateinit var tv_mission : TextView
    private lateinit var et_reponse : EditText
    private lateinit var responseLt : View

    private lateinit var button: Button
    private lateinit var buttonDelete: Button
    private lateinit var voirReponse : Button
    private lateinit var envoyer :Button

    private lateinit var response :TextView

    private var id = 0

    lateinit var session: LoginPref

    override fun finish() {
            val aj = Intent(this,AlertList::class.java)
            startActivity(aj)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_view)

        this.setTitle("Alert")

        session = LoginPref(this)

        session.checkLogin()

        userModel = ViewModelProvider(this).get(UserModel::class.java)
        alertModel = ViewModelProvider(this).get(AlertModel::class.java)
        missionModel = ViewModelProvider(this).get(MissionModel::class.java)

        id = intent.getIntExtra("id",0)

        var a = alertModel.getalert(id.toLong())
        var u = userModel.getUser(a.chauffaurId)!!

        var m = missionModel.getMission(a.missionId)

        if(session.getType() == 1){
            alertModel.changeEtat(a.id)
        }

        tv_name = findViewById(R.id.tv_name)
        tv_type = findViewById(R.id.tv_type)
        tv_desc = findViewById(R.id.tv_desc)
        tv_mission = findViewById(R.id.tv_mission)
        et_reponse = findViewById(R.id.et_reponse)
        button = findViewById(R.id.Repondre)
        buttonDelete = findViewById(R.id.Delete)
        envoyer = findViewById(R.id.Envoyer)
        voirReponse = findViewById(R.id.Voir)
        response = findViewById(R.id.Reponse)
        responseLt = findViewById(R.id.reponselt)

        tv_name.text = u.name
        tv_desc.text = a.title

        if(m!=null) {
            tv_mission.text = m.title
        }else{
            tv_mission.text = a.missionId.toString()
        }

        if(a.type == 1){
            tv_type.text = "Probleme avec vehicule"
        }else{
            if(a.type == 2){
                tv_type.text = "probleme avec la route"
            }else{
                if(a.type == 3){
                    tv_type.text = "Probleme avec le client"
                }else{
                    if(a.type == 4){
                        tv_type.text ="Autre"
                    }
                }
            }
        }

        if(session.getType() != 1){
            button.isVisible = false
            buttonDelete.isVisible = true
            voirReponse.isVisible = true
        }

        button.setOnClickListener {

            var a = alertModel.getalert(id.toLong())
            if(!a.reponse.trim().isEmpty()){
                android.app.AlertDialog.Builder(this)
                    .setMessage("Vous avez deja repondu à cette alert vous voulez changé la reponse ?")
                    .setPositiveButton("Oui") { _, _ ->

                        button.isVisible = false
                        et_reponse.isVisible = true
                        envoyer.isVisible = true

                    }
                    .setNegativeButton("Annuler"){_,_->

                    }
                    .create()
                    .show()
            }else{

                button.isVisible = false
                et_reponse.isVisible = true
                envoyer.isVisible = true

            }
        }

        buttonDelete.setOnClickListener {
            android.app.AlertDialog.Builder(this)
                .setMessage("Confirmez-vous la suppression ?")
                .setPositiveButton("Oui") { _, _ ->

                    alertModel.deleteAlert(id.toLong())
                    val aj = Intent(this, ChauffeurPage
                    ::class.java)
                    startActivity(aj)

                }
                .setNegativeButton("Annuler"){_,_->

                }
                .create()
                .show()
        }

        envoyer.setOnClickListener {

            var reponse = et_reponse.text.toString().trim()
            if(reponse.isEmpty()){
                Toast.makeText(this,"Vous devez remplir le champs reponse", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                alertModel.Repondre(reponse,id.toLong())
                envoyer.isVisible = false
                et_reponse.isVisible = false
                Toast.makeText(this,"Reponse envoyé", Toast.LENGTH_SHORT).show()
            }
        }

        voirReponse.setOnClickListener {
            var a = alertModel.getalert(id.toLong())
            if(!a.reponse.trim().isEmpty()){
                response.text = a.reponse
                responseLt.isVisible = true
                voirReponse.isVisible = false
            }else{
                Toast.makeText(this,"Pas de reponse pour l'instant", Toast.LENGTH_SHORT).show()
            }
        }


    }
}