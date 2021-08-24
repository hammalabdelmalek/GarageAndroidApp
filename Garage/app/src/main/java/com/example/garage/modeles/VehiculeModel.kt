package com.example.garage.modeles

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.garage.GarageData
import com.example.garage.dao.VehiculeDao
import com.example.garage.data.User
import com.example.garage.data.Vehicule

class VehiculeModel(application: Application) : AndroidViewModel(application) {

    val dao : VehiculeDao = GarageData.getInstance(application).vehiculeDao()

    fun allVehicule():List<Vehicule>{
        var lsVehicule = emptyList<Vehicule>()
        val tr = Thread{

            try{
                lsVehicule = dao.loadAllVehicule()}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsVehicule
    }

    fun allFreeVehicule():List<Vehicule>{
        var lsVehicule = emptyList<Vehicule>()
        val tr = Thread{

            try{
                lsVehicule = dao.loadVehiculeFree()}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsVehicule
    }

    fun getVehicule(id:Long):List<Vehicule>{
        var lsVehicule = emptyList<Vehicule>()
        val tr = Thread{

            try{
                lsVehicule = dao.getVehicule(id)}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return lsVehicule
    }

    fun getVehicul(id:Long):Vehicule?{
        var lsVehicule = emptyList<Vehicule>()
        val tr = Thread{

            try{
                lsVehicule = dao.getVehicule(id)}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()

        if(lsVehicule.size > 0){
            return lsVehicule[0]
        }
        return null
    }

    fun ajouterVehicule(v: Vehicule): Long {

        var ls : Long = -1
        val tr = Thread{

            try{
                ls = dao.inserVehicule(v)}
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
        return ls
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

    fun Delete(id:Long){

        val tr = Thread{

            try{
                dao.deletVehicule(id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()

    }

    fun updateVehicule(titre:String,marque:String,modele:String,nbrPlace:Int,consomation:Float,annee:String,id:Long){

        val tr = Thread{

            try{
                dao.updateVehicule(titre,marque,modele,nbrPlace,consomation,annee,id)
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

    fun setControle(date:String ,id:Long){
        val tr = Thread{

            try{
                dao.setControle(date,id)
            }
            catch(e : SQLiteException){
                Log.e("SQL_ERREUR",e.toString())
            }

        }
        tr.start()
        tr.join()
    }


}