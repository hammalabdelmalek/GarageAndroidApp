package com.example.garage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.garage.data.Alert
import com.example.garage.data.Notif

@Dao
interface NotifDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertNotif(notif: Notif) : Long

    @Query("DELETE  FROM Notif WHERE id = :id")
    fun deleteNotif(id: Long)

    @Query("SELECT * FROM Notif")
    fun loadAllNotif(): List<Notif>

    @Query("SELECT * FROM Notif WHERE id =:id")
    fun getNotif(id: Long): List<Notif>

    @Query("SELECT * FROM Notif WHERE visibility = 1 ")
    fun loadAllVisibleNotif(): List<Notif>

    @Query("UPDATE Notif SET visibility = 1 WHERE id = :id")
    fun changeEtat(id:Long)

    @Query("DELETE  FROM Notif WHERE vehiculeId = :id")
    fun deleteNotifV(id: Long)


}