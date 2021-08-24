package com.example.garage.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.R
import com.example.garage.data.Mission
import kotlinx.android.synthetic.main.mission_item_layout.view.*


class MissionAdapter (): RecyclerView.Adapter<MissionAdapter.VH>() {

    var lsMission= emptyList<Mission>()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return lsMission.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.mission_item_layout, parent,false)

        return VH(v)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val infoView = holder.itemView
        val mission = lsMission[position]

        if(mission.etat == 2){
            infoView.all.setBackgroundColor(Color.parseColor("#00FF00"))
        }

        infoView.titreMission.text = mission.title
        infoView.adresseFin.text =mission.adreesseDebut+"-->"+mission.adresseFin
        infoView.nomChauffeur.text = mission.chauffaur
        infoView.vId.text = mission.vehiculeId.toString()
        infoView.mId.text = mission.id.toString()

    }

    fun setListChauffeur(info: List<Mission>){
        this.lsMission= info
        notifyDataSetChanged()
    }
}