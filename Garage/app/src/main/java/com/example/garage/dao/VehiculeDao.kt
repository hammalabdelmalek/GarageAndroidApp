package com.example.garage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.garage.data.User
import com.example.garage.data.Vehicule

@Dao
interface VehiculeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun inserVehicule(vehicule: Vehicule) : Long

    @Query("DELETE  FROM Vehicule WHERE id = :id")
    fun deletVehicule(id: Long)

    @Query("SELECT * FROM Vehicule ")
    fun loadAllVehicule(): List<Vehicule>

    @Query("SELECT * FROM Vehicule WHERE etat ")
    fun loadVehiculeFree(): List<Vehicule>

    @Query("SELECT * FROM Vehicule WHERE id = :id ")
    fun getVehicule(id:Long): List<Vehicule>

    @Query("UPDATE Vehicule SET etat = :etat WHERE id = :id")
    fun changeEtat(etat :Int ,id:Long)

    @Query("UPDATE Vehicule SET modele =:modele ,marque =:marque ,nbrPlace =:nbrPlace ,consomation =:consomation , anneeSerculation = :annee, titre =:titre WHERE id = :id")
    fun updateVehicule(titre:String,marque:String,modele:String,nbrPlace:Int,consomation :Float,annee:String,id:Long)

    @Query("UPDATE Vehicule SET kilometrage = :k WHERE id = :id")
    fun setKilometrage(k: Float,id:Long)

    @Query("UPDATE Vehicule SET controleTechnique = :date WHERE id = :id")
    fun setControle(date:String ,id:Long)


}