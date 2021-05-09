package com.example.pushnotification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUp : AppCompatActivity() {
    lateinit var fName:String
    lateinit var sName:String
    lateinit var email:String
    lateinit var password:String

    companion object{
        lateinit var token:String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        getToken()
        btn_signUp.setOnClickListener {
            if(txt_firstName.text.isNotEmpty() && txt_firstName.text.toString() != ""){
                fName = txt_firstName.text.toString()
            }
            if(txt_secondName.text.isNotEmpty() && txt_secondName.text.toString() != ""){
                sName = txt_secondName.text.toString()
            }
            if(txt_email.text.isNotEmpty() && txt_email.text.toString() != ""){
                email = txt_email.text.toString()
            }
            if(txt_password.text.isNotEmpty() && txt_password.text.toString() != ""){
                password = txt_password.text.toString()
            }
            createNewUser(fName,sName,email,password)
            val i = Intent(this,SignIn::class.java)
            startActivity(i)
        }

        txtSignIn.setOnClickListener {
            val i = Intent(this,SignIn::class.java)
            startActivity(i)
        }
    }

    private fun createNewUser(firstName:String,secondName:String,email:String,password:String){
        val url="https://mcc-users-api.herokuapp.com/add_new_user"
        val queue= Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, url,Response.Listener { response ->
            Log.e("TAG add_new_user",response)
        },Response.ErrorListener { error ->
            Log.e("TAG error","error")
        }
        ){
            override fun getParams(): MutableMap<String, String> {
                val hashMap = HashMap<String,String>()
                hashMap["firstName"] = firstName
                hashMap["secondName"] = secondName
                hashMap["email"] = email
                hashMap["password"] = password

                return hashMap
            }
        }
        queue.add(request)
    }

    fun getToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {task ->
                if (!task.isSuccessful) {
                    Log.e("TAG", "error token")
                }
                token = task.result.toString()
                Log.e("TAG token","token is $token")
            }
    }
}