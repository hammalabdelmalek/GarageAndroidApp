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
import com.example.garage.data.User
import com.example.garage.modeles.UserModel
import kotlinx.android.synthetic.main.activity_admin_connection.*
import kotlinx.android.synthetic.main.user_item_layout.*

class AjouterChauffeur : AppCompatActivity() {

    private lateinit var userModel: UserModel
    private lateinit var nametv : EditText
    private lateinit var emailtv : EditText
    private lateinit var passwordtv : EditText
    private lateinit var numeroEt: EditText
    private lateinit var password2tv : EditText
    private lateinit var villeEt :EditText
    private lateinit var buttonUpdate : Button
    private lateinit var buttonAjout: Button
    private lateinit var buttonPassword: Button
    private lateinit var title :TextView

    lateinit var session: LoginPref
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_chauffeur)

        userModel = ViewModelProvider(this).get(UserModel::class.java)

        session = LoginPref(this)

        i = intent.getIntExtra("okk",0)

        nametv = findViewById(R.id.nom)
        emailtv = findViewById(R.id.email)
        passwordtv = findViewById(R.id.password)
        password2tv = findViewById(R.id.password2)
        numeroEt = findViewById(R.id.NumeroTelephone)
        villeEt = findViewById(R.id.ville)
        title = findViewById(R.id.title)
        buttonAjout = findViewById(R.id.Ajout)
        buttonUpdate = findViewById(R.id.Update)
        buttonPassword = findViewById(R.id.ModifierPassword)

        Update()

        buttonUpdate.setOnClickListener {
            //ici on fait le Update
            UpdateChauffeur()
        }

    }

    fun ChangePassword(Button: View){

        val password = passwordtv.text.toString().trim()
        val password2 = password2tv.text.toString().trim()
        if(password.isEmpty() || password2.isEmpty() ){
            Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }else{
            if(!testPassword(password)){
                Toast.makeText(this, "Le mot de passe doit etre minimum 6 caracteres", Toast.LENGTH_SHORT).show()
                return
            }

            if( !password.equals(password2)){
                Toast.makeText(this, "Les mots de passe ne corespond pas", Toast.LENGTH_SHORT).show()
                return
            }
            userModel.setPassword(password,session.getId())
            Toast.makeText(this,"Votre mot de passe a bien eté changé",Toast.LENGTH_SHORT).show()
            passwordtv.text.clear()
            password2tv.text.clear()
            return
        }

    }

    fun AddChauffeur(Button:View){

        val name = nametv.text.toString().trim()
        val mail = emailtv.text.toString().trim()
        val password = passwordtv.text.toString().trim()
        val password2 = password2tv.text.toString().trim()
        val numero = numeroEt.text.toString().trim()
        val ville = villeEt.text.toString().trim()

        if(name.isEmpty() || mail.isEmpty()|| password.isEmpty() || password2.isEmpty() || numero.isEmpty() || ville.isEmpty()){
            Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }else{

            if(!testNumero(numero)){
                Toast.makeText(this, "Donner un numero de telephone valide 0XXXXXXXXX", Toast.LENGTH_SHORT).show()
                return
            }

            if(!testMail(mail)){
                Toast.makeText(this, "Forme de mail invalid, exmple de forme : Name@mail.com", Toast.LENGTH_SHORT).show()
                return
            }
            if(!testPassword(password)){
                Toast.makeText(this, "Le mot de passe doit etre minimum 6 caracteres", Toast.LENGTH_SHORT).show()
                return
            }

            if( !password.equals(password2)){
                Toast.makeText(this, "Les mots de passe ne corespond pas", Toast.LENGTH_SHORT).show()
                return
            }

            val u = User(name,mail,password,numero,ville,0)
            val i = userModel.ajouterUser(u)
            nametv.text.clear()
            emailtv.text.clear()
            passwordtv.text.clear()
            Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show()
            val aj = Intent(this, ChauffeurList::class.java)
            startActivity(aj)
        }

    }

    fun UpdateChauffeur(){

        val name = nametv.text.toString().trim()
        val mail = emailtv.text.toString().trim()
        val numero = numeroEt.text.toString().trim()
        val ville = villeEt.text.toString().trim()

        if(name.isEmpty() || mail.isEmpty() || numero.isEmpty() || ville.isEmpty()){
            Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }else{

            if(!testNumero(numero)){
                Toast.makeText(this, "Donner un numero de telephone valide 0XXXXXXXXX", Toast.LENGTH_SHORT).show()
                return
            }

            if(!testMail(mail)){
                Toast.makeText(this, "Forme de mail invalid, exmple de forme : Name@mail.com", Toast.LENGTH_SHORT).show()
                return
            }
            val id = intent.getIntExtra("id",0)
            userModel.updateChauffeur(name,mail,numero,ville,id.toLong())
            val aj = Intent(this, AdminPage::class.java)
            startActivity(aj)
        }

    }


    fun Update(){

        if(i == 5){
            passwordtv.isVisible = false
            password2tv.isVisible = false
            val id = intent.getIntExtra("id",0)
            val u = userModel.getUser(id.toLong())
            if(u!=null){
                nametv.setText(u.name)
                emailtv.setText(u.mail)
                numeroEt.setText(u.telephone)
                villeEt.setText(u.ville)
                buttonUpdate.isVisible = true
                buttonAjout.isVisible = false
                title.text = "Modification Chauffeur"
            }
        }

        if(i==6){
            nametv.isVisible = false
            emailtv.isVisible = false
            numeroEt.isVisible = false
            villeEt.isVisible = false
            buttonAjout.isVisible = false
            title.text = "Changement de mot de passe"
            buttonPassword.isVisible = true
        }
    }

    fun testPassword(s:String):Boolean{
        if(s.length < 6){
            return false
        }
        return true
    }

    fun isNumber(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

    fun testNumero(s:String):Boolean{
        if(s.length != 10){
            return false
        }else{
            if(!isNumber(s)){
                return false
            }else{
                if(s[0] != '0'){
                    return false
                }
            }
        }
        return true
    }



    fun testMail(s:String):Boolean{

        var a = s.split("@").toTypedArray()

        if(a.size != 2){
            return false
        }

        var k = a[1]

        var a2 = k.split(".").toTypedArray()

        if(a2.size != 2){
            return false
        }
        return true
    }
}