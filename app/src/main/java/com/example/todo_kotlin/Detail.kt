package com.example.todo_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_kotlin.databinding.ActivityDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Detail : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var id: String
    lateinit var d_date: String
    lateinit var d_title: String
    lateinit var d_des: String

    lateinit var new_date: String
    lateinit var new_title: String
    lateinit var new_des: String

    val database = Firebase.database
    val myRef = database.getReference("Registered data")
        .child(FirebaseAuth.getInstance()!!.currentUser!!.uid).child("todo")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

//        binding.detailDateId.text=intent.getStringExtra("date")
//        binding.detailTitleId.text=intent.getStringExtra("title")
//        binding.detailDescriptionId.text=intent.getStringExtra("des")



        binding.edittextDetailDescriptionId.isEnabled = false
        binding.edittextDetailTitleId.isEnabled = false
        binding.edittextDetailDateId.isEnabled = false

        id=intent.getStringExtra("id").toString()
        d_date = intent.getStringExtra("date").toString()
        d_des = intent.getStringExtra("des").toString()
        d_title = intent.getStringExtra("title").toString()
        binding.edittextDetailDateId.setText(d_date)
        binding.edittextDetailTitleId.setText(d_title)
        binding.edittextDetailDescriptionId.setText(d_des)


    }

    fun deleteDate(view: android.view.View) {
        myRef.child(id).removeValue(null)
        Toast.makeText(
            this,
            "Task" + intent.getStringExtra("title") + " is Deleted",
            Toast.LENGTH_SHORT
        ).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun update(view: android.view.View) {


        binding.edittextDetailDescriptionId.isEnabled = true
        binding.edittextDetailTitleId.isEnabled = true
        binding.edittextDetailDateId.isEnabled = true

        binding.edittextDetailDescriptionId.setBackgroundResource(R.drawable.rectangle_edit)
        binding.edittextDetailTitleId.setBackgroundResource(R.drawable.rectangle_edit)
        binding.edittextDetailDateId.setBackgroundResource(R.drawable.rectangle_edit)





        binding.btnUpdateTask.setOnClickListener(View.OnClickListener {
            new_date=binding.edittextDetailDateId.text.toString()
            new_title=binding.edittextDetailTitleId.text.toString()
            new_des=binding.edittextDetailDescriptionId.text.toString()
            if (!d_date.equals(new_date)) {
                myRef.child(id).child("date").setValue(new_date);
            //    Toast.makeText(this, "date is change", Toast.LENGTH_SHORT).show()
                binding.edittextDetailDateId.setText(new_date)
            }
            if (!d_title.equals(new_title)) {
                myRef.child(id).child("title").setValue(new_title);
             //   Toast.makeText(this, "title is change", Toast.LENGTH_SHORT).show()
                binding.edittextDetailTitleId.setText(new_title)

            }
            if (!d_des.equals(new_des)) {
                myRef.child(id).child("description").setValue(new_des);
              //  Toast.makeText(this, "description is chagnge", Toast.LENGTH_SHORT).show()
                binding.edittextDetailDescriptionId.setText(new_des)
            }
            if(!d_date.equals(new_date)||!d_title.equals(new_title)||!d_des.equals(new_des)){
                Toast.makeText(this, "data updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))

            }
            else{
                Toast.makeText(this, "Data is same", Toast.LENGTH_SHORT).show()
            }


//            if (isDateChanged() || isTitleChanged() || isDescriptionChanged()) {
//                Toast.makeText(this, "Data is updated", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Data is same and unable to update", Toast.LENGTH_SHORT).show()
//            }
        })


    }


    private fun isDateChanged(): Boolean {
        if (!d_date.equals(new_date)) {
            myRef.child(id).child("date").setValue(new_date);
            Toast.makeText(this, "date is change", Toast.LENGTH_SHORT).show()
            binding.edittextDetailDateId.setText(new_date)
            return true;
        } else {
            return false;
        }

    }
    private fun isTitleChanged(): Boolean {
        if (!d_title.equals(new_title)) {
            myRef.child(id).child("title").setValue(new_title);
            Toast.makeText(this, "title is change", Toast.LENGTH_SHORT).show()
            binding.edittextDetailTitleId.setText(new_title)
            return true;
        } else {
            return false;
        }
    }
    private fun isDescriptionChanged(): Boolean {
        if (!d_des.equals(new_des)) {
            myRef.child(id).child("description").setValue(new_des);
            Toast.makeText(this, "description is chagnge", Toast.LENGTH_SHORT).show()
            binding.edittextDetailDescriptionId.setText(new_des)
            return true;
        } else {
            return false;
        }
    }
}