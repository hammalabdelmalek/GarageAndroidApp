package com.example.garage.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notif(

    @PrimaryKey(autoGenerate =true)
    var id : Long= 0,
    var desc : String,
    var vehiculeId: Long,
    var type : Int,
    var date : String,
    var visibility : Boolean = false

){
    constructor(desc: String,vehiculeId: Long,date: String):this(0,desc,vehiculeId,0,date)
}
