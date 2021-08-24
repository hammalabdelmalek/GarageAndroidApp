package com.example.garage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.Views.AlertView
import com.example.garage.adapters.AlertAdapter
import com.example.garage.adapters.NotifAdapter
import com.example.garage.data.Alert
import com.example.garage.data.Notif
import com.example.garage.modeles.AlertModel
import com.example.garage.modeles.NotifModel
import kotlinx.android.synthetic.main.alert_item_layout.view.*
import kotlinx.android.synthetic.main.notif_item_layout.view.*

class NotifList : AppCompatActivity() {

    private var adapter: NotifAdapter = NotifAdapter()
    lateinit var notifModel: NotifModel
    lateinit var session: LoginPref
    lateinit var view: View
    private var lsnotif = emptyList<Notif>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif_list)

        this.setTitle("Liste des Notifications")

        session = LoginPref(this)
        notifModel = ViewModelProvider(this).get(NotifModel::class.java)

        session.checkLogin()
        session.checkAdmin()

        view = findViewById(R.id.Empty)

        lsnotif = notifModel.allVisibleNotif()

        if(lsnotif.size != 0){
            view.isVisible = false
        }

        val recyclerView = findViewById(R.id.recycler) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        adapter.setListNotif(lsnotif)

    }

    fun Change(view : View){

        val aj = Intent(this, AjouteVehicule::class.java)
        aj.putExtra("id",view.nId.text.toString().trim().toInt())
        aj.putExtra("okk",6)
        startActivity(aj)

    }
}