package com.example.garage.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Alert(
    @PrimaryKey(autoGenerate =true)
    var id : Long= 0,
    var title : String,
    var chauffaurId: Long,
    var missionId:Long,
    var vehiculeId: Long,
    var type : Int,
    var reponse : String,
    var isConsulted : Boolean = false

){
    constructor(title: String,chauffaurId: Long,missionId: Long,vehiculeId: Long,type: Int):this(0,title,chauffaurId,missionId,vehiculeId,type,"")
}
