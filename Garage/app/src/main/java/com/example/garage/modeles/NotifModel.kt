package com.example.garage.modeles

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.garage.GarageData
import com.example.garage.dao.NotifDao
import com.example.garage.data.Notif

class NotifModel (application: Application) : AndroidViewModel(application) {

    val dao : NotifDao = GarageData.getInstance(application).notifDao()


    fun ajouterNotif(notif: Notif):Long{
        var l:Long = 0
        val tr = Thread{

            try{
                l = dao.insertNotif(notif)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return l
    }

    fun allNotif():List<Notif>{

        var lsNotif = emptyList<Notif>()
        val tr = Thread{

            try{
                lsNotif = dao.loadAllNotif()
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsNotif
    }

    fun getNotif(id: Long):Notif{

        var lsNotif = emptyList<Notif>()
        val tr = Thread{

            try{
                lsNotif = dao.getNotif(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsNotif[0]
    }

    fun allVisibleNotif():List<Notif>{

        var lsNotif = emptyList<Notif>()
        val tr = Thread{

            try{
                lsNotif = dao.loadAllVisibleNotif()
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsNotif
    }

    fun changeEtat(id:Long){
        val tr = Thread{

            try {
                dao.changeEtat(id)
            }catch (e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }
        }
        tr.start()
        tr.join()
    }

    fun deleteNotif(id:Long){
        val tr = Thread{

            try {
                dao.deleteNotif(id)
            }catch (e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }
        }
        tr.start()
        tr.join()
    }

    fun deleteNotifV(id:Long){
        val tr = Thread{

            try {
                dao.deleteNotifV(id)
            }catch (e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }
        }
        tr.start()
        tr.join()
    }



}