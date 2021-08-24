package com.example.garage.modeles

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.garage.GarageData
import com.example.garage.dao.MissionDao
import com.example.garage.data.Mission


class MissionModel (application: Application) : AndroidViewModel(application) {

    val dao : MissionDao = GarageData.getInstance(application).missionDao()

    fun inserMission(m: Mission):Long{
        var l:Long = 0
        val tr = Thread{

            try{
                l = dao.inserMission(m)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return l
    }

    fun deleteMission(id:Long){

        val tr = Thread{

            try{
                 dao.deleteMission(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()

    }

    fun allMission():List<Mission>{

        var lsMission = emptyList<Mission>()
        val tr = Thread{

            try{
                lsMission = dao.loadAllMissions()
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsMission

    }

    fun getMission(id: Long): Mission? {

        var lsMission = emptyList<Mission>()
        val tr = Thread{

            try{
                lsMission = dao.getMission(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        if(lsMission.size>0){
            return lsMission[0]
        }else{
            return null
        }

    }

    fun allChauffeurMission(id:Long):List<Mission>{
        var lsMission= emptyList<Mission>()
        val tr = Thread{

            try{
                lsMission = dao.loadChauffeurMissions(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsMission
    }

    fun chauffeurEncoursMission(id: Long):List<Mission>{
        var lsMission= emptyList<Mission>()
        val tr = Thread{

            try{
                lsMission = dao.loadChauffeurMissionsEnCours(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsMission
    }

    fun chauffeurFiniMission(id: Long):List<Mission>{
        var lsMission= emptyList<Mission>()
        val tr = Thread{

            try{
                lsMission = dao.loadChauffeurMissionsFini(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsMission
    }

    fun chauffeurNonCommenceMission(id: Long):List<Mission>{
        var lsMission= emptyList<Mission>()
        val tr = Thread{

            try{
                lsMission = dao.loadChauffeurMissionsNonCommance(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsMission
    }

    fun changeEtat(etat: Int, id: Long){

        val tr = Thread{

            try{
                dao.changeEtat(etat,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()

    }

    fun updateMission(titre:String,adresseDebut:String,adresseFin:String,dateDebbut:String,dateLimit:String,id:Long){
        val tr = Thread{

            try{
                dao.updateMission(titre,adresseDebut,adresseFin,dateDebbut,dateLimit,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
    }

    fun noter(id: Long){
        val tr = Thread{

            try{
                dao.Noter(1,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
    }

}