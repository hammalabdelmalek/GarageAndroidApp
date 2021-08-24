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
import com.example.garage.data.Alert
import com.example.garage.modeles.AlertModel
import kotlinx.android.synthetic.main.alert_item_layout.view.*



class AlertList : AppCompatActivity() {

    private var adapter: AlertAdapter = AlertAdapter()
    lateinit var alertModel: AlertModel
    lateinit var session: LoginPref
    lateinit var view: View
    private var lsalert = emptyList<Alert>()

    override fun finish() {
        val aj = Intent(this,AdminPage::class.java)
        startActivity(aj)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_list)

        this.setTitle("Liste des alerts")

        session = LoginPref(this)
        alertModel = ViewModelProvider(this).get(AlertModel::class.java)
        view = findViewById(R.id.Empty)
        session.checkLogin()

        if(session.getType() == 1){
            lsalert = alertModel.allAlert()
        }else{
            lsalert = alertModel.allChauffeurAlerts(session.getId())
        }

        if(lsalert.size != 0){
            view.isVisible = false
        }

        val recyclerView = findViewById(R.id.recycler) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        adapter.setListChauffeur(lsalert)
    }

    fun alertView(view : View){
        val aj = Intent(this,AlertView::class.java)
        aj.putExtra("id",view.aId.text.toString().trim().toInt())
        startActivity(aj)
    }


}