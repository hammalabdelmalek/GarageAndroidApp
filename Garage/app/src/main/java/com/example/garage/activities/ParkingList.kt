package com.example.garage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.adapters.ParkingAdapter
import com.example.garage.adapters.VehiculeAdapter
import com.example.garage.data.Notif
import com.example.garage.data.Parking
import com.example.garage.data.Vehicule
import com.example.garage.modeles.NotifModel
import com.example.garage.modeles.ParkingModel
import com.example.garage.modeles.VehiculeModel
import kotlinx.android.synthetic.main.parking_item_layout.view.*
import kotlinx.android.synthetic.main.vehicule_item_layout.view.*
import kotlinx.android.synthetic.main.vehicule_item_layout.view.vId

class ParkingList : AppCompatActivity() {

    private var adapter: ParkingAdapter = ParkingAdapter()
    lateinit var parkingModel: ParkingModel
    lateinit var notifModel: NotifModel
    lateinit var vehiculeModel: VehiculeModel
    lateinit var session: LoginPref
    private var lsParking = emptyList<Parking>()
    private  var i = 0
    private  var desc = ""
    private  var ann = ""
    private var control = ""
    private var conso = 0F
    private var nbr = 0
    private var marque = ""
    private var modele = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_list)

        this.setTitle("Liste des place parking")

        session = LoginPref(this)

        parkingModel = ViewModelProvider(this).get(ParkingModel::class.java)
        vehiculeModel = ViewModelProvider(this).get(VehiculeModel::class.java)
        notifModel = ViewModelProvider(this).get(NotifModel::class.java)

        session.checkLogin()

        i = intent.getIntExtra("okk",0)

        if(i == 3){
            lsParking = parkingModel.allFreeParking()
        }else{
            lsParking = parkingModel.allParking()
        }

        remplir()

        val recyclerView = findViewById(R.id.recycler) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        adapter.setListChauffeur(lsParking)

    }

    fun remplir(){

        if(i == 3){
            Toast.makeText(this, "Choisir la place de parking pour mettre la voiture", Toast.LENGTH_SHORT).show()
            desc = intent.getStringExtra("desc")!!
            ann = intent.getStringExtra("ann")!!
            control = intent.getStringExtra("control")!!
            nbr = intent.getIntExtra("nbr",0)
            conso = intent.getFloatExtra("conso",0F)
            marque = intent.getStringExtra("marque")!!
            modele = intent.getStringExtra("modele")!!

        }

    }

    fun addVehicule(view: View){

        if(i == 3){

            var j = view.pId.text.toString().trim().toInt().toLong()

            var v = Vehicule(desc,marque,modele,nbr,conso,ann,control,true,j)

            var a = control.split("/").toTypedArray()
            var d = ""
            if(a[1].toInt() > 1 ){
                var new = a[1].toInt()-1
                d = a[0]+"/"+new.toString()+"/"+a[2]
            }else{
                var new = a[2].toInt()-1
                d =a[0]+"/"+"12"+"/"+new.toString()
            }

            val id = vehiculeModel.ajouterVehicule(v)
            var notif = Notif("Votre vehecule "+marque+" "+modele+" a moins q'un mois pour refaire le coontrole technique",id,d)

            notifModel.ajouterNotif(notif)
            parkingModel.changeEtat(0,j)

            val aj = Intent(this, AdminPage::class.java)
            startActivity(aj)

        }

    }

}