package com.example.garage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.R
import com.example.garage.data.Alert
import kotlinx.android.synthetic.main.alert_item_layout.view.*

class AlertAdapter  (): RecyclerView.Adapter<AlertAdapter.VH>() {

    var lsAlert = emptyList<Alert>()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return lsAlert.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.alert_item_layout, parent,false)

        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val infoView = holder.itemView
        val alert = lsAlert[position]

        infoView.title.text = alert.title
        infoView.aId.text = alert.id.toString()

        if(!alert.isConsulted){
            infoView.newAlert.isVisible = true
        }

    }

    fun setListChauffeur(info: List<Alert>){
        this.lsAlert= info
        notifyDataSetChanged()
    }
}