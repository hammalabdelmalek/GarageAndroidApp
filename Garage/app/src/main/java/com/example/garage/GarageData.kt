package com.example.garage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.garage.dao.*
import com.example.garage.data.*

@Database(entities = [User::class, Mission::class, Parking::class,Vehicule::class, Alert::class,Notif::class], version = 1)

abstract class GarageData :RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun missionDao(): MissionDao
    abstract fun parkingDao(): ParkingDao
    abstract fun vehiculeDao(): VehiculeDao
    abstract fun alertDao(): AlertDao
    abstract fun notifDao(): NotifDao

    companion object {
        @Volatile
        private var INSTANCE: GarageData? = null

        fun getInstance(context: Context): GarageData {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GarageData::class.java,
                    "Garage"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }

        }
    }

}




