package com.jesuspeirolopez.myflexagenda

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat

class DayAdapter(private val context: Context, private val days: List<String>, private val month: String, private val year: String) : BaseAdapter() {

    //Funciones básicas para los días del mes en el calendario
    override fun getCount(): Int = days.size

    override fun getItem(position: Int): Any = days[position]

    override fun getItemId(position: Int): Long = position.toLong()

    //Para que los días del mes funcionen como botones para moverse al dia concreto
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button = Button(context)
        button.text = days[position]

        button.backgroundTintList = ContextCompat.getColorStateList(context, R.color.lighter_blue)

        button.setOnClickListener {

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("day", days[position])
            intent.putExtra("month", month)
            intent.putExtra("year", year)
            context.startActivity(intent)

        }
        return button
    }
}