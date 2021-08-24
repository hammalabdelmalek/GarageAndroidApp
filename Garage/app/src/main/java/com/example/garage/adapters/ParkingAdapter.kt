package com.example.garage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.R
import com.example.garage.data.Parking
import kotlinx.android.synthetic.main.parking_item_layout.view.*
import kotlinx.android.synthetic.main.parking_item_layout.view.etat
import kotlinx.android.synthetic.main.vehicule_item_layout.view.*

class ParkingAdapter (): RecyclerView.Adapter<ParkingAdapter.VH>() {

    var lsParking= emptyList<Parking>()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return lsParking.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.parking_item_layout, parent,false)

        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val infoView = holder.itemView
        val parking = lsParking[position]



        infoView.numero.text = "Place N"+(parking.id)
        if(parking.etat){
            infoView.etat.text = "Libre"
        }else{
            infoView.etat.text = "Occupé"
        }

        infoView.pId.setText(parking.id.toString())

    }

    fun setListChauffeur(info: List<Parking>){
        this.lsParking= info
        notifyDataSetChanged()
    }
}