package com.example.garage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.garage.data.Alert


@Dao
interface AlertDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun inserAlert(alert: Alert) : Long

    @Query("DELETE  FROM Alert WHERE id = :id")
    fun deleteAlert(id: Long)

    @Query("SELECT * FROM Alert ")
    fun loadAllAlert(): List<Alert>

    @Query("SELECT * FROM Alert WHERE isConsulted = 0")
    fun loadAllNonConsultedAlert(): List<Alert>

    @Query("SELECT * FROM Alert where chauffaurId =:id ")
    fun loadAlertParChauffeur(id:Long): List<Alert>

    @Query("SELECT * FROM Alert WHERE missionId = :id")
    fun loadAlertParMission(id:Long): List<Alert>

    @Query("UPDATE Alert SET isConsulted = 1 WHERE id = :id")
    fun changeEtat(id:Long)

    @Query("UPDATE Alert SET reponse = :reponse WHERE id = :id")
    fun Repondre(reponse:String,id:Long)

    @Query("SELECT * FROM Alert WHERE id = :id")
    fun getAlert(id:Long): List<Alert>

}