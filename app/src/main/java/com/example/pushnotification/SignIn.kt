package com.example.pushnotification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pushnotification.SignUp.Companion.token
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    lateinit var email:String
    lateinit var password:String
    //var token:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        btn_signIn.setOnClickListener {
            if(txtEmail.text.isNotEmpty() && txtEmail.text.toString() != ""){
                email = txtEmail.text.toString()
            }
            if(txtPassword.text.isNotEmpty() && txtPassword.text.toString() != ""){
                password = txtPassword.text.toString()
            }

            checkEmailAndPassword(email,password)
            val i = Intent(this,MainActivity::class.java)
            i.putExtra("email",email)
            i.putExtra("password",password)
            startActivity(i)
        }

        txtSignUp.setOnClickListener {
            val i = Intent(this,SignUp::class.java)
            startActivity(i)
        }

    }

    private fun checkEmailAndPassword(email:String, password:String){
        val url="https://mcc-users-api.herokuapp.com/login"
        val queue= Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, url, Response.Listener { response ->
            Log.e("TAG login",response)

        }, Response.ErrorListener { error ->
            Log.e("TAG error",error.toString())
        }
        ){
            override fun getParams(): MutableMap<String, String> {
                val hashMap = HashMap<String,String>()
                hashMap["email"] = email
                hashMap["password"] = password

                //addRegistrationToken(email,password,token)
                return hashMap
            }
        }
        queue.add(request)
    }

//    private fun addRegistrationToken(email:String, password:String, regToken:String){
//        val url="https://mcc-users-api.herokuapp.com/add_reg_token"
//        val queue= Volley.newRequestQueue(this)
//        val request = object : StringRequest(Method.PUT, url, Response.Listener { response ->
//            Log.e("TAG add_reg_token",response)
//
//        }, Response.ErrorListener { error ->
//            Log.e("TAG error","error")
//        }
//        ){
//            override fun getParams(): MutableMap<String, String> {
//                val hashMap = HashMap<String,String>()
//                hashMap["email"] = email
//                hashMap["password"] = password
//                hashMap["reg_token"] = regToken
//                return hashMap
//            }
//        }
//        queue.add(request)
//    }

}