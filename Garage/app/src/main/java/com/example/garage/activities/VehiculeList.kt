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
import com.example.garage.Views.VehiculeView
import com.example.garage.adapters.ChauffeurAdapter
import com.example.garage.adapters.VehiculeAdapter
import com.example.garage.data.User
import com.example.garage.data.Vehicule
import com.example.garage.modeles.UserModel
import com.example.garage.modeles.VehiculeModel
import kotlinx.android.synthetic.main.vehicule_item_layout.view.*

class VehiculeList : AppCompatActivity() {

    private var adapter: VehiculeAdapter = VehiculeAdapter()
    lateinit var vehiculeModel: VehiculeModel
    lateinit var session: LoginPref
    private var lsVehicule = emptyList<Vehicule>()
    private lateinit var view: View
    private var i = 0
    private var titre = ""
    private var adresseDebut = ""
    private var adresseFin =""
    private var dateDebut =""
    private var dateLimit =""
    private var kilo = 0F

    fun remplir(){

        if(i == 3){
            Toast.makeText(this, "Choisir le Vehicule pour la mission", Toast.LENGTH_SHORT).show()
            titre = intent.getStringExtra("titre")!!
            adresseDebut = intent.getStringExtra("adresseDebut")!!
            adresseFin = intent.getStringExtra("adresseFin")!!
            dateDebut = intent.getStringExtra("dateDebut")!!
            dateLimit = intent.getStringExtra("dateLimit")!!
            kilo = intent.getFloatExtra("kilo",0F)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicule_list)

        this.setTitle("Liste des vehicules")

        session = LoginPref(this)
        vehiculeModel = ViewModelProvider(this).get(VehiculeModel::class.java)

        session.checkLogin()

        view = findViewById(R.id.Empty)

        i = intent.getIntExtra("okk",0)

        remplir()
        if(i!=3){
            lsVehicule = vehiculeModel.allVehicule()
        }else{
            lsVehicule = vehiculeModel.allFreeVehicule()
        }

        if(lsVehicule.size != 0){
            view.isVisible = false
        }

        val recyclerView = findViewById(R.id.recycler) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        adapter.setListChauffeur(lsVehicule)

    }

    fun AddMission(view: View){
        if(i == 3){
            val aj = Intent(this, ChauffeurList::class.java)
            aj.putExtra("titre",titre)
            aj.putExtra("adresseFin",adresseFin)
            aj.putExtra("adresseDebut",adresseDebut)
            aj.putExtra("dateDebut",dateDebut)
            aj.putExtra("dateLimit",dateLimit)
            aj.putExtra("kilo",kilo)
            aj.putExtra("vId",view.vId.text.toString().trim().toInt())
            aj.putExtra("okk",3)
            startActivity(aj)
        }else{
            val aj = Intent(this, VehiculeView::class.java)
            aj.putExtra("id",view.vId.text.toString().trim().toInt())
            startActivity(aj)
        }
    }
}