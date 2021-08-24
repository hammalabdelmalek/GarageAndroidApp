package com.example.garage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.garage.data.Parking

@Dao
interface ParkingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun inserParking(parking: Parking) : Long

    @Query("DELETE  FROM Parking WHERE id = :id")
    fun deleteParking(id: Long)

    @Query("SELECT * FROM Parking ")
    fun loadAllParking(): List<Parking>

    @Query("SELECT * FROM Parking WHERE etat ")
    fun loadParkingFree(): List<Parking>

    @Query("UPDATE Parking SET etat = :etat WHERE id = :id")
    fun changeEtat(etat :Int ,id:Long)

}