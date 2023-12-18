package com.jesuspeirolopez.myflexagenda

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Toast

class DayAdapter(private val context: Context, private val days: List<String>) : BaseAdapter() {

    override fun getCount(): Int = days.size

    override fun getItem(position: Int): Any = days[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button = Button(context)
        button.text = days[position]
        button.setOnClickListener {

            //Poner el que hacer con el boton del dia

        }
        return button
    }
}