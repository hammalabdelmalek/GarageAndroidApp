package com.example.garage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.garage.activities.Admin_connection
import com.example.garage.activities.ChauffeurPage
import com.example.garage.activities.MainActivity

class LoginPref {

    lateinit var pref : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    lateinit var con : Context

    var privateMode : Int = 0

    constructor(con: Context){
        this.con = con
        pref = con.getSharedPreferences(PREF_NAME,privateMode)
        editor = pref.edit()

    }

    companion object{
        val PREF_NAME ="Login_preference"
        val IS_LOGIN = "isloggedin"
        val KEY_NAME = "name"
        val KEY_EMAIL = "email"
        val KEY_TYPE = "type"
        val KEY_ID = "id"
    }

    fun createLoginSession(name:String,email:String,type:Int,id:Long){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString(KEY_NAME,name)
        editor.putString(KEY_EMAIL,email)
        editor.putInt(KEY_TYPE,type)
        editor.putLong(KEY_ID,id)
        editor.commit()
    }

    fun checkLogin(){
        if(!this.isloggedin()){
            val aj :Intent = Intent(con, Admin_connection::class.java)
            aj.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            aj.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(aj)
        }
    }

    fun checkAdmin(){
        checkLogin()
        var id = pref.getInt(KEY_TYPE,0)
        if(id != 1){
            val aj :Intent = Intent(con, ChauffeurPage::class.java)
            aj.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            aj.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(aj)
        }
    }

    fun getUserDetail():HashMap<String,String>{
        var user: Map<String,String> = HashMap<String,String>()
        (user as HashMap).put(KEY_NAME,pref.getString(KEY_NAME,null)!!)
        (user as HashMap).put(KEY_EMAIL, pref.getString(KEY_EMAIL,null)!!)
        return user
    }

    fun logoutUser(){
        editor.clear()
        editor.commit()
        val i :Intent = Intent(con, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        con.startActivity(i)
    }

    fun isloggedin():Boolean{
        return pref.getBoolean(IS_LOGIN,false)
    }

    fun getType():Int{
        return pref.getInt(KEY_TYPE,1)
    }

    fun getId():Long{
        return pref.getLong(KEY_ID,0)
    }
}