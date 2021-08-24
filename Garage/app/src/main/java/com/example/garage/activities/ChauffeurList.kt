package com.example.garage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.Views.ChauffeurView
import com.example.garage.adapters.ChauffeurAdapter
import com.example.garage.data.Mission
import com.example.garage.data.User
import com.example.garage.modeles.MissionModel
import com.example.garage.modeles.UserModel
import com.example.garage.modeles.VehiculeModel
import kotlinx.android.synthetic.main.user_item_layout.view.*

class ChauffeurList : AppCompatActivity() {

    private var adapter: ChauffeurAdapter = ChauffeurAdapter(this)
    lateinit var userModel:UserModel
    lateinit var missionModel: MissionModel
    lateinit var vehiculeModel: VehiculeModel
    private lateinit var view: View
    lateinit var session: LoginPref
    private var lsChauffeur = emptyList<User>()
    private var i = 0
    private var titre = ""
    private var adresseDebut = ""
    private var adresseFin =""
    private var dateDebbut =""
    private var dateLimit =""
    private var vId = 0
    private var kilo = 0F

    override fun finish() {
        if(session.getType() == 1){
            val aj = Intent(this,AdminPage::class.java)
            startActivity(aj)
        }else{
            super.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chauffeur_list)

        this.setTitle("Liste des chauffeurs")

        i = intent.getIntExtra("okk",0)

        remplir()

        view  = findViewById(R.id.Empty)

        session = LoginPref(this)
        userModel = ViewModelProvider(this).get(UserModel::class.java)
        missionModel = ViewModelProvider(this).get(MissionModel::class.java)
        vehiculeModel = ViewModelProvider(this).get(VehiculeModel::class.java)

        session.checkLogin()

        lsChauffeur = userModel.getAllChauffeur()

        if(lsChauffeur.size != 0){
            view.isVisible = false
        }

        val recyclerView = findViewById(R.id.recycler) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        adapter.setListChauffeur(lsChauffeur)


    }

    fun remplir(){

        if(i == 3){
            Toast.makeText(this, "Choisir le chauffeur pour la mission", Toast.LENGTH_SHORT).show()
            titre = intent.getStringExtra("titre")!!
            adresseDebut = intent.getStringExtra("adresseDebut")!!
            adresseFin = intent.getStringExtra("adresseFin")!!
            dateDebbut = intent.getStringExtra("dateDebut")!!
            dateLimit = intent.getStringExtra("dateLimit")!!
            vId = intent.getIntExtra("vId",0)
            kilo = intent.getFloatExtra("kilo",0F)

        }
    }

    fun Addmission(button: View){

        if(i == 3){

            var e = button.mail.text.toString().trim()
            var n = button.name.text.toString().trim()

            var u :User = userModel.getChauffeurByMail(e)

            var mission : Mission = Mission(titre,adresseDebut,adresseFin,dateDebbut,dateLimit,u.id,u.name,vId.toLong(),kilo,0)
            missionModel.inserMission(mission)

            vehiculeModel.changeEtat(0,mission.vehiculeId)

            val aj = Intent(this, AdminPage::class.java)
            startActivity(aj)
        }else{
            val aj = Intent(this, ChauffeurView::class.java)
            aj.putExtra("id",button.userId.text.toString().trim().toInt())
            startActivity(aj)
        }

    }

}




