package com.example.todo_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_kotlin.Model.RegistrationModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference("Registered data")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()
        var reg_name: TextView = findViewById(R.id.register_name)
        var reg_num: TextView = findViewById(R.id.register_Number)
        var reg_email: TextView = findViewById(R.id.register_email)
        var reg_password: TextView = findViewById(R.id.register_password)
        var reg_btn: Button = findViewById(R.id.btn_register)
        auth = Firebase.auth

        reg_btn.setOnClickListener(View.OnClickListener {

            if (reg_name.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Fill Name", Toast.LENGTH_SHORT).show()
            }
            if (reg_email.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Fill Email", Toast.LENGTH_SHORT).show()
            }
            if (reg_num.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Fill Number", Toast.LENGTH_SHORT).show()
            }
            if (reg_password.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Fill Password", Toast.LENGTH_SHORT).show()
            } else {

                var name: String = reg_name.text.toString()
                var email: String = reg_email.text.toString()
                var number: String = reg_num.text.toString()
                var password: String = reg_password.text.toString()
                // val id:String=FirebaseAuth.getInstance().currentUser.uid



                auth.createUserWithEmailAndPassword(
                    reg_email.text.toString(),
                    reg_password.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth!!.currentUser!!.uid
                           // val idd = auth.currentUser.uid
                            startActivity(Intent(this, MainActivity::class.java))
                            val model = RegistrationModel(name, email, userId, number, password)

                            myRef.child(userId).setValue(model).addOnCompleteListener(
                                OnCompleteListener {
                                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                                }).addOnFailureListener(OnFailureListener { Exception ->
                                Toast.makeText(this, "" + Exception.message, Toast.LENGTH_SHORT)
                                    .show()
                            })

                            finish()
                        } else {
                            Toast.makeText(this, "Authentecation failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        })
    }
    fun register_login(view: View) {
        startActivity(Intent(this, Login::class.java))
    }
}