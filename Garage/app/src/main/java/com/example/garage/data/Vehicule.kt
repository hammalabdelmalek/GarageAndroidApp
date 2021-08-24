package com.example.garage.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index( value = ["anneeSerculation"], unique = true)])
data class Vehicule(
    @PrimaryKey(autoGenerate =true)
    var id : Long= 0,
    var titre : String,
    var marque : String,
    var modele : String,
    var nbrPlace : Int,
    var consomation : Float,
    var anneeSerculation: String,
    var controleTechnique :String,
    var etat : Boolean,
    var kilometrage : Float,
    var parkingId:Long

){
    constructor(titre: String,marque: String,modele: String,nbrPlace: Int,consomation: Float,anneeSerculation: String,controleTechnique: String,etat: Boolean,parkingId: Long) : this(0,titre,marque,modele,nbrPlace,consomation,anneeSerculation,controleTechnique,etat,0F,parkingId)
}
