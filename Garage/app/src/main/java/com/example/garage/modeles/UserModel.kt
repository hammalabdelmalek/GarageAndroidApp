package com.example.garage.modeles

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.garage.GarageData
import com.example.garage.data.User
import com.example.garage.dao.UserDao

class UserModel(application: Application) : AndroidViewModel(application) {

    val dao : UserDao = GarageData.getInstance(application).userDao()

    fun allUsers(m:String,p:String):List<User>{
        var lsUser = emptyList<User>()
        val tr = Thread{

            try{
                lsUser = dao.loadAllUsers(m,p) }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsUser
    }

    fun getUser(id:Long): User? {
        var lsUser = emptyList<User>()
        val tr = Thread{

            try{
                lsUser = dao.getUser(id) }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()

        if(lsUser.size > 0){
            return lsUser[0]
        }

        return null
    }

    fun getChauffeur(m: String,p: String) : List<User>{
        var lsUser = emptyList<User>()
        val tr = Thread{

            try{
                lsUser = dao.loadChauffeur(m,p) }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsUser
    }

    fun getAllChauffeur() : List<User>{
        var lsUser = emptyList<User>()
        val tr = Thread{

            try{
                lsUser = dao.loadAllChauffeur() }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsUser
    }



    fun getAdmin(m: String,p: String) : List<User>{
        var lsUser = emptyList<User>()
        val tr = Thread{

            try{
                lsUser = dao.loadAdmin(m,p) }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsUser
    }

    fun ajouterUser(u: User): Long {

        var ls : Long = -1
        val tr = Thread{

            try{
                ls = dao.insertUser(u)}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return ls
    }

    fun getChauffeurByMail(m:String):User{
        var lsUser = emptyList<User>()
        val tr = Thread{

            try{
                lsUser = dao.loadAdminBymail(m) }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsUser[0]
    }

    fun deleteUser(id:Long){

        val tr = Thread{

            try{
                dao.deleteUser(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()

    }

    fun updateChauffeur(name:String,mail:String,telephone:String,ville:String ,id:Long){
        val tr = Thread{

            try{
                dao.updateChauffeur(name,mail,telephone,ville,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
    }

    fun noter(note:Float,id: Long){
        val tr = Thread{

            try{
                dao.noter(note,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
    }

    fun setNumroMission(n: Int,id:Long){

        val tr = Thread{

            try{
                dao.setNumroMission(n,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()

    }

    fun setKilometrage(k: Float,id:Long){
        val tr = Thread{

            try{
                dao.setKilometrage(k,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
    }

    fun setPassword(p: String,id:Long){
        val tr = Thread{

            try{
                dao.setPassword(p,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
    }

}