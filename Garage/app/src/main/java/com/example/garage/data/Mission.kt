package com.example.garage.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.nio.channels.FileLock
import java.util.*

@Entity
data class Mission(
    @PrimaryKey(autoGenerate =true)
    var id : Long= 0,
    var title : String,
    var adreesseDebut : String,
    var adresseFin :String,
    var dateDebut : String,
    var dateLimit : String,
    var chauffaurId: Long,
    var chauffaur : String,
    var vehiculeId: Long,
    var note : Boolean,
    var kilometrage : Float,
    var etat : Int

){
   constructor(title: String,adreesseDebut: String,adresseFin: String,dateDebut: String,dateLimit: String,chauffaurId: Long,chauffaur: String,vehiculeId: Long,kilometrage: Float,etat: Int):this(0,title,adreesseDebut,adresseFin,dateDebut,dateLimit,chauffaurId,chauffaur,vehiculeId,false,kilometrage,etat)
}


