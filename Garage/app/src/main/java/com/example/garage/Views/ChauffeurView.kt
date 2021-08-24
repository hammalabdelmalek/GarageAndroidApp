package com.example.garage.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.activities.AdminPage
import com.example.garage.activities.AjouterChauffeur
import com.example.garage.modeles.MissionModel
import com.example.garage.modeles.ParkingModel
import com.example.garage.modeles.UserModel
import com.example.garage.modeles.VehiculeModel
import kotlinx.android.synthetic.main.activity_chauffeur_view.*

class ChauffeurView : AppCompatActivity() {


    private lateinit var userModel: UserModel
    private lateinit var missionModel: MissionModel


    private lateinit var tv_name : TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_numero : TextView
    private lateinit var tv_nbrMission : TextView
    private lateinit var tv_kilometrage :TextView
    private lateinit var tv_ville : TextView
    private lateinit var tv_note :TextView


    private lateinit var button: Button

    private lateinit var button2: Button

    private var id = 0

    lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chauffeur_view)

        this.setTitle("Chauffeur")

        session = LoginPref(this)

        session.checkLogin()

        session.checkAdmin()

        tv_name= findViewById(R.id.tv_name)
        tv_email = findViewById(R.id.tv_email)
        tv_numero = findViewById(R.id.tv_phone)
        tv_nbrMission = findViewById(R.id.tv_nbr_mission)
        tv_kilometrage = findViewById(R.id.kilometrage)
        tv_ville = findViewById(R.id.tv_address)
        tv_note = findViewById(R.id.note)

        button = findViewById(R.id.delete)
        button2 =findViewById(R.id.update)

        userModel = ViewModelProvider(this).get(UserModel::class.java)
        missionModel = ViewModelProvider(this).get(MissionModel::class.java)

        id = intent.getIntExtra("id",0)

        var u = userModel.getUser(id.toLong())!!

        tv_email.text = u.mail
        tv_name.text = u.name
        tv_numero.text = u.telephone
        tv_nbrMission.text = u.nbrMission.toString()
        tv_kilometrage.text = u.kilometrage.toString()
        tv_ville.text = u.ville.toString()
        tv_note.text = u.note.toString()


        button.setOnClickListener{

            val lm = missionModel.chauffeurEncoursMission(id.toLong())
            if(lm.size != 0){
                Toast.makeText(this,"Vous ne pouvez pas supprimer ce chauffeur il est en cours d'une mission",Toast.LENGTH_SHORT).show()
            }else{

                android.app.AlertDialog.Builder(this)
                    .setMessage("Confirmez-vous la suppression ?")
                    .setPositiveButton("Oui") { _, _ ->

                        userModel.deleteUser(id.toLong())
                        val aj = Intent(this, AdminPage::class.java)
                        startActivity(aj)

                    }
                    .setNegativeButton("Annuler"){_,_->

                    }
                    .create()
                    .show()

            }
        }

        button2.setOnClickListener {
            Toast.makeText(this,"ici en fait la modification",Toast.LENGTH_SHORT).show()
            val aj = Intent(this,AjouterChauffeur::class.java)
            aj.putExtra("id",id)
            aj.putExtra("okk",5)
            startActivity(aj)
        }
    }
}