package com.example.garage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.data.Alert
import com.example.garage.modeles.AlertModel

class AddAlert : AppCompatActivity() {

    private var desc = ""
    private var i = 0
    private lateinit var descEt : EditText
    private lateinit var choixEt : EditText
    private lateinit var session: LoginPref
    private lateinit var alertModel: AlertModel
    private var id : Long = 0
    private var id2 :Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alert)

        session = LoginPref(this)
        alertModel = ViewModelProvider(this).get(AlertModel::class.java)

        session.checkLogin()

        id = intent.getIntExtra("id",0).toLong()
        id2 = intent.getIntExtra("id2",0).toLong()

    }

    fun AjouteAlert(button: View){

        descEt = findViewById(R.id.descAlert)
        choixEt = findViewById(R.id.typeAlert)

        desc = descEt.text.toString().trim()
        var k = choixEt.text.toString().trim()

        if(desc.isEmpty() || k.isEmpty()){
            Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }else{
            i = k.toInt()
            if(i<1 || i >4){
                Toast.makeText(this, "Donner un choix valide pour le type entre 1 et 4", Toast.LENGTH_SHORT).show()
                return
            }else{

                var a = Alert(desc,session.getId(),id,id2,i)
                alertModel.ajouterAlert(a)
                val aj = Intent(this,ChauffeurPage::class.java)
                startActivity(aj)

            }
        }

    }

}