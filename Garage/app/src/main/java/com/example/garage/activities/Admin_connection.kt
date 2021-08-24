package com.example.garage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.garage.*
import com.example.garage.data.User
import com.example.garage.modeles.ParkingModel
import com.example.garage.modeles.UserModel

class Admin_connection : AppCompatActivity() {

    private lateinit var userModel: UserModel
    private lateinit var parkingModel: ParkingModel
    lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_connection)

        session = LoginPref(this)
        userModel = ViewModelProvider(this).get(UserModel::class.java)
        parkingModel = ViewModelProvider(this).get(ParkingModel::class.java)

        if(session.isloggedin()){

            var i : Intent = Intent(applicationContext, AdminPage::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()

        }

    }

    fun connection(button:View){
        var mail : EditText = findViewById(R.id.email)
        var motdepasse : EditText = findViewById(R.id.motdepasse)

        if(mail.text.toString().trim().isEmpty()|| motdepasse.text.toString().trim().isEmpty()){
            Toast.makeText(this@Admin_connection, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        var lsusers = userModel.getAdmin(mail.text.toString().trim(),motdepasse.text.toString().trim())

        if(lsusers.size != 1){
            mail.text.clear()
            motdepasse.text.clear()
            Toast.makeText(this@Admin_connection, "connection failed try again", Toast.LENGTH_SHORT).show()
            return
        }else{
            var us : User = lsusers[0]
            session.createLoginSession(us.name,mail.text.toString().trim(),us.type,us.id)
            var i = Intent(applicationContext, AdminPage::class.java)
            startActivity(i)
            finish()
        }

    }

    fun ajouter () : Long{
        val u = User("malek","malek","malek","0557034917","Thenia",1)
        val i = userModel.ajouterUser(u)
        return i;
    }

}