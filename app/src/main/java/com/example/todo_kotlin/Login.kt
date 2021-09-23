package com.example.todo_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        var log_email: TextView = findViewById(R.id.login_email)
        var log_password: TextView = findViewById(R.id.login_password)
        var log_btn:Button=findViewById(R.id.btn_login)

      //  var log_btn: Button =findViewById(R.id.btn_login)
        auth = Firebase.auth



        log_btn.setOnClickListener(View.OnClickListener {

            if(log_email.text.toString().isEmpty()){
                Toast.makeText(this, "Please Fill Email", Toast.LENGTH_SHORT).show()
            }
            if(log_password.text.toString().isEmpty()){
                Toast.makeText(this, "Please Fill Password", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(log_email.text.toString(),log_password.text.toString()).addOnCompleteListener(
                    OnCompleteListener {
                        startActivity(Intent(this,MainActivity::class.java))
                    }).addOnFailureListener(OnFailureListener {
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                })
            }


        })



    }

    fun signup(view: View) {
        startActivity(Intent(this,Registration::class.java))
    }
}