package com.example.garage.modeles

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.garage.GarageData
import com.example.garage.dao.ParkingDao
import com.example.garage.dao.VehiculeDao
import com.example.garage.data.Parking
import com.example.garage.data.Vehicule

class ParkingModel(application: Application) : AndroidViewModel(application) {
    
    val dao : ParkingDao = GarageData.getInstance(application).parkingDao()

    fun insertParking(p:Parking):Long{
        var l:Long = 0
        val tr = Thread{

            try{
                l = dao.inserParking(p)}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return l
    }

    fun ParkingData(){
        var p :Parking

        for(i in 1..20){
            p = Parking(-1,true)
            insertParking(p)
        }
    }

    fun allParking():List<Parking>{
        var lsParking = emptyList<Parking>()
        val tr = Thread{

            try{
                lsParking = dao.loadAllParking()}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsParking
    }

    fun allFreeParking():List<Parking>{
        var lsParking= emptyList<Parking>()
        val tr = Thread{

            try{
                lsParking = dao.loadParkingFree()}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsParking
    }

    fun changeEtat(etat:Int,id:Long){
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
}