package com.example.garage.modeles

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.garage.GarageData
import com.example.garage.dao.AlertDao
import com.example.garage.dao.MissionDao
import com.example.garage.data.Alert
import com.example.garage.data.Mission

class AlertModel (application: Application) : AndroidViewModel(application) {

    val dao : AlertDao = GarageData.getInstance(application).alertDao()

    fun ajouterAlert(alert: Alert):Long{
        var l:Long = 0
        val tr = Thread{

            try{
                l = dao.inserAlert(alert)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return l
    }

    fun allAlert():List<Alert>{

        var lsAlert = emptyList<Alert>()
        val tr = Thread{

            try{
                lsAlert = dao.loadAllAlert()
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsAlert
    }

    fun getalert(id :Long):Alert{
        var lsAlert = emptyList<Alert>()
        val tr = Thread{

            try{
                lsAlert = dao.getAlert(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsAlert[0]
    }

    fun allChauffeurAlerts(id:Long):List<Alert>{
        var lsAlert= emptyList<Alert>()
        val tr = Thread{

            try{
                lsAlert = dao.loadAlertParChauffeur(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsAlert
    }

    fun deleteAlert(id:Long){
        val tr = Thread{

            try {
                dao.deleteAlert(id)
            }catch (e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }
        }
        tr.start()
        tr.join()
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

    fun Repondre(reponse:String,id:Long){

        val tr = Thread{

            try {
                dao.Repondre(reponse,id)
            }catch (e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }
        }
        tr.start()
        tr.join()

    }

    fun newMessages() : Boolean{
        var lsAlert = emptyList<Alert>()
        val tr = Thread{

            try{
                lsAlert = dao.loadAllNonConsultedAlert()
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()

        if(lsAlert.size>0){
            return true
        }else{
            return false
        }
    }

}