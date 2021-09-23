package com.example.todo_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.example.todo_kotlin.Model.NewToDo_Model
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NewToDo : AppCompatActivity() {

    lateinit var date:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_to_do)
        supportActionBar?.hide()

        var title:TextView=findViewById(R.id.title_id)
        var description:TextView=findViewById(R.id.description_id)
        var btn_createtodo:Button=findViewById(R.id.btn_newtodo_create)
        val myRef = FirebaseDatabase.getInstance().getReference("Registered data").child(FirebaseAuth.getInstance()!!.currentUser!!.uid).child("todo")

        var calenderview:CalendarView=findViewById(R.id.calendarView)
        calenderview?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
             date = ""+dayOfMonth + "/" + (month + 1) + "/" + year
            Toast.makeText(this, date, Toast.LENGTH_SHORT).show()
        }

        btn_createtodo.setOnClickListener(View.OnClickListener {

            var todo_title:String=title.text.toString()
            var todo_description:String=description.text.toString()
            var todo_id:String=myRef.push().key!!

            var todo_model= NewToDo_Model(todo_title,todo_description,date,todo_id)

            myRef.child(todo_id).setValue(todo_model).addOnCompleteListener {
                startActivity(Intent(this,MainActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Unable to create task try again", Toast.LENGTH_SHORT).show()
            }

        })

    }
}

