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
import com.example.garage.modeles.UserModel

class ChauffeurConnexion : AppCompatActivity() {
    private lateinit var userModel: UserModel
    lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chauffeur_connexion)

        session = LoginPref(this)
        userModel = ViewModelProvider(this).get(UserModel::class.java)

        if(session.isloggedin()){
            var i : Intent = Intent(applicationContext, AdminPage::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }

    }

    fun connection(button: View){

        var mail : EditText = findViewById(R.id.email)
        var motdepasse : EditText = findViewById(R.id.motdepasse)

        if(mail.text.toString().trim().isEmpty()|| motdepasse.text.toString().trim().isEmpty()){
            Toast.makeText(this ,"Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        var lsusers = userModel.getChauffeur(mail.text.toString().trim(),motdepasse.text.toString().trim())

        if(lsusers.size != 1){
            mail.text.clear()
            motdepasse.text.clear()
            Toast.makeText(this, "connection failed try again", Toast.LENGTH_SHORT).show()
            return
        }else{
            var us : User = lsusers[0]
            session.createLoginSession(us.name,mail.text.toString().trim(),us.type,us.id)
            var i = Intent(applicationContext, ChauffeurPage::class.java)
            startActivity(i)
            finish()
        }

    }
}