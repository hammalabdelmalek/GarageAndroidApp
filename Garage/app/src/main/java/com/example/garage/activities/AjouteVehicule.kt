package com.example.garage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.garage.LoginPref
import com.example.garage.R
import com.example.garage.data.Notif
import com.example.garage.data.User
import com.example.garage.data.Vehicule
import com.example.garage.modeles.NotifModel
import com.example.garage.modeles.ParkingModel
import com.example.garage.modeles.UserModel
import com.example.garage.modeles.VehiculeModel

class AjouteVehicule : AppCompatActivity() {

    private lateinit var vehiculeModel: VehiculeModel
    private lateinit var parkingModel :ParkingModel
    private lateinit var notifModel: NotifModel
    private lateinit var titreEt : EditText
    private lateinit var anneeEt : EditText
    private lateinit var controleTechniqueEt :EditText
    private lateinit var marqueEt : EditText
    private lateinit var modeleEt : EditText
    private lateinit var nbrEt : EditText
    private lateinit var consoEt : EditText
    private lateinit var buttonAjout : Button
    private lateinit var buttonUpdate : Button
    private lateinit var buttonRenouvler: Button
    private lateinit var title :TextView

    var i = 0

    lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajoute_vehicule)

        session = LoginPref(this)

        session.checkLogin()

        session.checkAdmin()

        vehiculeModel = ViewModelProvider(this).get(VehiculeModel::class.java)
        parkingModel = ViewModelProvider(this).get(ParkingModel::class.java)
        notifModel = ViewModelProvider(this).get(NotifModel::class.java)

        i = intent.getIntExtra("okk",0)

        titreEt = findViewById(R.id.titre)
        anneeEt = findViewById(R.id.anneeSerculation)
        controleTechniqueEt = findViewById(R.id.controle)
        title = findViewById(R.id.title)
        marqueEt = findViewById(R.id.marque)
        modeleEt = findViewById(R.id.modele)
        nbrEt = findViewById(R.id.nbr_place)
        consoEt = findViewById(R.id.consomation)
        buttonAjout = findViewById(R.id.Ajout)
        buttonUpdate = findViewById(R.id.Update)
        buttonRenouvler = findViewById(R.id.Renouvler)

        Update()

        buttonUpdate.setOnClickListener {
            UpdateVehicule()
        }

        buttonRenouvler.setOnClickListener {
            val control = controleTechniqueEt.text.toString().trim()
            val id = intent.getIntExtra("id",0)
            val n = notifModel.getNotif(id.toLong())
            val lv = vehiculeModel.getVehicule(n.vehiculeId)
            val v = lv[0]
            var controls= control.split("/")
            val date = v.controleTechnique.split("/")
            if(date[2].toInt()+1 > controls[2].toInt()){
                Toast.makeText(this,"Donner une date valide le renouvellement est pour une durée d\'une annéé minimum",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                notifModel.deleteNotif(id.toLong())
                vehiculeModel.setControle(control,v.id)

                var a = control.split("/").toTypedArray()
                var d = ""
                if(a[1].toInt() > 1 ){
                    var new = a[1].toInt()-1
                    d = a[0]+"/"+new.toString()+"/"+a[2]
                }else{
                    var new = a[2].toInt()-1
                    d =a[0]+"/"+"12"+"/"+new.toString()
                }
                
                var notif = Notif("Votre vehecule "+v.marque+" "+v.modele+" a moins q'un mois pour refaire le coontrole technique",v.id,d)

                notifModel.ajouterNotif(notif)

                val aj = Intent(this,AdminPage::class.java)
                startActivity(aj)
            }
        }

    }

    fun AddVehicule(button:View){

        var lp = parkingModel.allFreeParking()

        if(lp.size == 0){

            Toast.makeText(this, "Pas de place de parking libre pour mettre le vehicule ils sont tous occupé", Toast.LENGTH_SHORT).show()
            return

        }

        val titre = titreEt.text.toString().trim()
        val annee = anneeEt.text.toString().trim()
        val control = controleTechniqueEt.text.toString().trim()
        val marque = marqueEt.text.toString().trim()
        val modele = modeleEt.text.toString().trim()
        val nbrPlace = nbrEt.text.toString().trim()
        val conso = consoEt.text.toString().trim()


        if(titre.isEmpty() || annee.isEmpty()|| control.isEmpty()|| marque.isEmpty() || modele.isEmpty() || nbrPlace.isEmpty() || conso.isEmpty()){
            Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }else{

            if(!testDate(control)){
               Toast.makeText(this, "Donner date de fin de controle technique valide jj/mm/aaaa", Toast.LENGTH_SHORT).show()
                return
            }
            if(!isNumber(nbrPlace)){
                Toast.makeText(this, "Donner un nombre de place valide", Toast.LENGTH_SHORT).show()
                return
            }

            if(!isFloat(conso)){
                Toast.makeText(this, "Donner une consomation par 100 kilometre valide", Toast.LENGTH_SHORT).show()
                return
            }

            val aj = Intent(this, ParkingList::class.java)

            aj.putExtra("okk",3)
            aj.putExtra("desc",titre)
            aj.putExtra("ann",annee)
            aj.putExtra("control",control)
            aj.putExtra("marque",marque)
            aj.putExtra("modele",modele)
            aj.putExtra("nbr",nbrPlace.toInt())
            aj.putExtra("conso",conso.toFloat())

            startActivity(aj)
        }

    }

    fun UpdateVehicule(){

        val titre = titreEt.text.toString().trim()
        val annee = anneeEt.text.toString().trim()
        val marque = marqueEt.text.toString().trim()
        val modele = modeleEt.text.toString().trim()
        val nbrPlace = nbrEt.text.toString().trim()
        val conso = consoEt.text.toString().trim()

        if(titre.isEmpty() || annee.isEmpty()|| marque.isEmpty() || modele.isEmpty() || nbrPlace.isEmpty() || conso.isEmpty()){
            Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }else{


            if(!isNumber(nbrPlace)){
                Toast.makeText(this, "Donner un nombre de place valide", Toast.LENGTH_SHORT).show()
                return
            }

            if(!isFloat(conso)){
                Toast.makeText(this, "Donner une consomation par 100 kilometre valide", Toast.LENGTH_SHORT).show()
                return
            }

            val id = intent.getIntExtra("id",0)
            vehiculeModel.updateVehicule(titre,marque,modele,nbrPlace.toInt(),conso.toFloat(),annee,id.toLong())
            val aj = Intent(this, AdminPage::class.java)
            startActivity(aj)
        }

    }

    fun isNumber(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

    fun isFloat(s: String): Boolean {
        return try {
            s.toFloat()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

    fun testDate(s:String):Boolean{

        var a = s.split("/").toTypedArray()
        if(a.size != 3){
            return false
        }
        for(elem in a){
            if(!isNumber(elem)){
                return false
            }
        }
        if(a[0].toInt() > 31 || a[0].toInt() < 1){
            return false
        }
        if(a[1].toInt() > 12 || a[1].toInt() < 1){
            return false
        }
        if(a[2].toInt() < 1 ){
            return false
        }

        return true

    }

    fun Update(){
        if(i == 5){
            val id = intent.getIntExtra("id",0)
            val lv = vehiculeModel.getVehicule(id.toLong())
            val v = lv[0]
            titreEt.setText(v.titre)
            anneeEt.setText(v.anneeSerculation)
            controleTechniqueEt.isVisible = false
            modeleEt.setText(v.modele)
            marqueEt.setText(v.marque)
            nbrEt.setText(v.nbrPlace.toString())
            consoEt.setText(v.consomation.toString())
            title.text = "Modification vehicule"
            buttonAjout.isVisible = false
            buttonUpdate.isVisible = true
        }
        if(i == 6){

            val id = intent.getIntExtra("id",0)
            val n = notifModel.getNotif(id.toLong())
            val lv = vehiculeModel.getVehicule(n.vehiculeId)
            val v = lv[0]
            titreEt.isVisible = false
            anneeEt.isVisible = false
            controleTechniqueEt.setText(v.controleTechnique)
            modeleEt.isVisible = false
            marqueEt.isVisible = false
            consoEt.isVisible = false
            nbrEt.isVisible= false
            title.text = "Renouvlement du controle technique du vehicule"
            buttonAjout.isVisible = false
            buttonUpdate.isVisible = false
            buttonRenouvler.isVisible = true

        }
    }

}

