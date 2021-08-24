package com.example.garage.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Parking(
    @PrimaryKey(autoGenerate =true)
    var id : Long= 0,
    var vehiculeId : Long,
    var etat : Boolean
){
    constructor(vehiculeId: Long,etat: Boolean) : this(0,vehiculeId,etat)
}
