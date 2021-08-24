package com.example.garage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.R
import com.example.garage.data.Parking
import com.example.garage.data.Vehicule
import kotlinx.android.synthetic.main.parking_item_layout.view.*
import kotlinx.android.synthetic.main.parking_item_layout.view.etat
import kotlinx.android.synthetic.main.vehicule_item_layout.view.*

class VehiculeAdapter(): RecyclerView.Adapter<VehiculeAdapter.VH>() {

    var lsVehicule= emptyList<Vehicule>()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return lsVehicule.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.vehicule_item_layout, parent,false)

        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val infoView = holder.itemView
        val vehicule = lsVehicule[position]

        infoView.titre.text = vehicule.marque+" "+vehicule.modele
        infoView.annee.text = vehicule.anneeSerculation
        infoView.vId.setText(vehicule.id.toString())

        if(vehicule.etat){
            infoView.etat.text = "Libre"
        }else{
            infoView.etat.text = "Occupé"
        }
    }

    fun setListChauffeur(info: List<Vehicule>){
        this.lsVehicule= info
        notifyDataSetChanged()
    }

}


