package com.example.garage.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.garage.R
import com.example.garage.activities.AdminPage
import com.example.garage.data.Mission
import com.example.garage.data.User
import kotlinx.android.synthetic.main.user_item_layout.view.*

class ChauffeurAdapter(var mContext:Context): RecyclerView.Adapter<ChauffeurAdapter.VH>() {

    var lsChauffeur = emptyList<User>()

    private lateinit var m : Mission

    private var missionexiste = 0

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return lsChauffeur.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.user_item_layout, parent,false)

        val onClick = View.OnClickListener { view ->


                for(c in lsChauffeur){
                    if(c.mail == v.mail.text.toString().trim()){
                        m.chauffaurId = c.id
                        m.chauffaur = c.name
                    }
                }

                val aj = Intent(mContext, AdminPage::class.java)
                aj.putExtra("titre",m.title)
                aj.putExtra("nomChauffeur",m.chauffaur)
                aj.putExtra("adresseFin",m.adresseFin)
                aj.putExtra("adresseDebut",m.adreesseDebut)
                aj.putExtra("dateDebut",m.dateDebut)
                aj.putExtra("dateLimit",m.dateLimit)
                aj.putExtra("chaufId",m.chauffaurId)
                aj.putExtra("okk",3)
                mContext.startActivity(aj)



        }

        //v.setOnClickListener (onClick)

        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val infoView = holder.itemView
        val chauf = lsChauffeur[position]



        infoView.name.text = chauf.name
        infoView.mail.text = chauf.mail
        infoView.userId.text =chauf.id.toString()

    }

    fun setListChauffeur(info: List<User>){
        this.lsChauffeur= info
        notifyDataSetChanged()
    }

    fun setMission(mission:Mission){
        this.missionexiste = 1;
        this.m = mission
    }
}
