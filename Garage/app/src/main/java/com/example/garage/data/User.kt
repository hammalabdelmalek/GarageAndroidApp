package com.example.garage.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index( value = ["mail"], unique = true)])
data class User(

    @PrimaryKey(autoGenerate =true)
    var id:Long= 0,
    var name:String,
    var mail:String,
    var password:String,
    var telephone: String,
    var ville : String,
    var nbrMission: Int,
    var kilometrage: Int,
    var note:Float,
    var type:Int

){
    constructor(name: String,mail: String,password: String,telephone: String,ville: String,type: Int) : this(0,name,mail,password,telephone,ville,0,0,
        0F,type)
}

