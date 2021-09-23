package com.example.todo_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_kotlin.Adapter.TodolistAdapter
import com.example.todo_kotlin.Model.NewToDo_Model
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val database = Firebase.database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val recycler:RecyclerView=findViewById(R.id.rv_data)
        val todolist=ArrayList<NewToDo_Model>()
        val myRef = FirebaseDatabase.getInstance().getReference("Registered data").child(FirebaseAuth.getInstance()!!.currentUser!!.uid).child("todo")

        recycler.layoutManager=LinearLayoutManager(this)


//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Get Post object and use the values to update the UI
//                val model: NewToDo_Model? = dataSnapshot.getValue<NewToDo_Model>()
//                todolist.add(model!!)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//              //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        myRef.addValueEventListener(postListener)

        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(usersnapshot in snapshot.children){
                        val tasks=usersnapshot.getValue(NewToDo_Model::class.java)
                        todolist.add(tasks!!)
                    }
                    recycler.adapter=TodolistAdapter(todolist,this@MainActivity)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "unable to get the data", Toast.LENGTH_SHORT).show()
            }

        })







    }

    fun createNew(view: android.view.View) {
        startActivity(Intent(this,NewToDo::class.java))

    }
    fun logOut(view: android.view.View) {
        finish()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this,Login::class.java))


    }
}