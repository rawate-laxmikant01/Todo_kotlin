package com.example.todo_kotlin.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_kotlin.Detail
import com.example.todo_kotlin.Model.NewToDo_Model
import com.example.todo_kotlin.R

class TodolistAdapter(val todolist: ArrayList<NewToDo_Model>,val context: Context):
    RecyclerView.Adapter<TodolistAdapter.todoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): todoViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_custom_layout, parent, false)

        return todoViewHolder(view)
    }

    override fun onBindViewHolder(holder: todoViewHolder, position: Int) {
        holder.date.text=todolist[position].date
        holder.title.text=todolist[position].title
        holder.cardview.setOnClickListener(View.OnClickListener {
            val intent= Intent(context,Detail::class.java)
            intent.putExtra("date",todolist[position].date)
            intent.putExtra("title",todolist[position].title)
            intent.putExtra("des",todolist[position].description)
            intent.putExtra("id",todolist[position].ID)
            context.startActivity(intent)
        })


    }

    override fun getItemCount(): Int {
        return todolist.size
    }

    class todoViewHolder (ItemView: View) : RecyclerView.ViewHolder(ItemView){

        var date:TextView=itemView.findViewById(R.id.tv_date_id)
        var title:TextView=itemView.findViewById(R.id.tv_title_id)
        var cardview:CardView=itemView.findViewById(R.id.cardview_id)

    }
}