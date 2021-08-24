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
import com.example.garage.Views.MissionView
import com.example.garage.adapters.ChauffeurAdapter
import com.example.garage.adapters.MissionAdapter
import com.example.garage.data.Mission
import com.example.garage.data.User
import com.example.garage.modeles.MissionModel
import com.example.garage.modeles.ParkingModel
import com.example.garage.modeles.UserModel
import kotlinx.android.synthetic.main.mission_item_layout.view.*

class MissionList : AppCompatActivity() {

    private var adapter: MissionAdapter = MissionAdapter()
    lateinit var missionModel: MissionModel
    lateinit var session: LoginPref
    lateinit var view: View
    private var lsMission = emptyList<Mission>()
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_list)

        this.setTitle("Liste des missions")

        session = LoginPref(this)
        missionModel = ViewModelProvider(this).get(MissionModel::class.java)

        session.checkLogin()

        view = findViewById(R.id.Empty)

        i = intent.getIntExtra("okk",0)

        if(session.getType() == 1){
            lsMission = missionModel.allMission()
        }else{
            lsMission = missionModel.allChauffeurMission(session.getId())
        }

        if(lsMission.size !=0){
            view.isVisible = false
        }

        if(i == 3){
            Toast.makeText(this,"Votre Alert concerne qu'elle mission",Toast.LENGTH_SHORT).show()
        }

        val recyclerView = findViewById(R.id.recycler) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        adapter.setListChauffeur(lsMission)

    }

    fun addAlert(view:View){

        if(i == 3){

            var id1 = view.mId.text.toString().trim().toInt()
            var id2 = view.vId.text.toString().trim().toInt()
            val aj = Intent(this,AddAlert::class.java)
            aj.putExtra("id",id1)
            aj.putExtra("id2",id2)
            startActivity(aj)

        }else{
            var id = view.mId.text.toString().trim().toInt()
            val aj = Intent(this,MissionView::class.java)
            aj.putExtra("id",id)
            startActivity(aj)
        }

    }
}