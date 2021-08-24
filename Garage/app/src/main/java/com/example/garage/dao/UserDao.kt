package com.example.garage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.garage.data.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: User) : Long

    @Query("DELETE  FROM User WHERE id = :id")
    fun deleteUser(id: Long)

    @Query("SELECT * FROM User where mail = :m AND password = :p")
    fun loadAllUsers(m:String, p:String): List<User>

    @Query("SELECT * FROM User where mail = :m AND password = :p AND type = 0")
    fun loadChauffeur(m:String, p:String): List<User>

    @Query("SELECT * FROM User where id = :id")
    fun getUser(id: Long): List<User>

    @Query("SELECT * FROM User where  type = 0")
    fun loadAllChauffeur(): List<User>

    @Query("SELECT * FROM User where mail = :m AND password = :p AND type = 1")
    fun loadAdmin(m:String, p:String): List<User>


    @Query("SELECT * FROM User where mail = :m ")
    fun loadAdminBymail(m:String): List<User>

    @Query("UPDATE User SET ville =:ville, mail = :mail, name =:name, telephone = :telephone WHERE id = :id")
    fun updateChauffeur(name:String,mail:String,telephone:String,ville:String ,id:Long)

    @Query("UPDATE User SET note = :note  WHERE id = :id")
    fun noter(note: Float,id:Long)

    @Query("UPDATE User SET nbrMission = :n WHERE id = :id")
    fun setNumroMission(n: Int,id:Long)

    @Query("UPDATE User SET kilometrage = :k WHERE id = :id")
    fun setKilometrage(k: Float,id:Long)

    @Query("UPDATE User SET password = :p WHERE id = :id")
    fun setPassword(p: String,id:Long)

}