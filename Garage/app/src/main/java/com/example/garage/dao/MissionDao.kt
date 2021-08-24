package com.example.garage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.garage.data.Mission

@Dao
interface MissionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun inserMission(mission: Mission) : Long

    @Query("DELETE  FROM Mission WHERE id = :id")
    fun deleteMission(id: Long)

    @Query("SELECT * FROM Mission ")
    fun loadAllMissions(): List<Mission>

    @Query("SELECT * FROM Mission WHERE id = :id")
    fun getMission(id: Long): List<Mission>

    @Query("SELECT * FROM Mission WHERE chauffaurId = :id")
    fun loadChauffeurMissions(id:Long): List<Mission>

    @Query("SELECT * FROM Mission WHERE chauffaurId = :id AND etat = 1")
    fun loadChauffeurMissionsEnCours(id:Long): List<Mission>

    @Query("SELECT * FROM Mission WHERE chauffaurId = :id AND etat = 2")
    fun loadChauffeurMissionsFini(id:Long): List<Mission>

    @Query("SELECT * FROM Mission WHERE chauffaurId = :id AND etat = 0")
    fun loadChauffeurMissionsNonCommance(id:Long): List<Mission>

    @Query("UPDATE Mission SET etat = :etat WHERE id = :id")
    fun changeEtat(etat :Int ,id:Long)

    @Query("UPDATE Mission SET note = :etat WHERE id = :id")
    fun Noter(etat :Int ,id:Long)

    @Query("UPDATE Mission SET title = :titre ,adreesseDebut = :adresseDebut ,adresseFin = :adresseFin ,dateDebut = :dateDebbut ,dateLimit = :dateLimit WHERE id = :id")
    fun updateMission(titre:String,adresseDebut:String,adresseFin:String,dateDebbut:String,dateLimit:String,id:Long)




}