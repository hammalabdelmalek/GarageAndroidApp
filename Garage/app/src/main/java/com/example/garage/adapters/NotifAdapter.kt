package com.example.garage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.R
import com.example.garage.data.Notif
import kotlinx.android.synthetic.main.alert_item_layout.view.title
import kotlinx.android.synthetic.main.notif_item_layout.view.*

class NotifAdapter (): RecyclerView.Adapter<NotifAdapter.VH>() {

    var lsNotif = emptyList<Notif>()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return lsNotif.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.notif_item_layout, parent,false)

        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val infoView = holder.itemView
        val alert = lsNotif[position]

        infoView.title.text = alert.desc
        infoView.nId.text = alert.id.toString()

    }

    fun setListNotif(info: List<Notif>){
        this.lsNotif= info
        notifyDataSetChanged()
    }
}