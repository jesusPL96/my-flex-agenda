package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class BirthdayAdapter(private val birthdays: LiveData<List<BirthdayMO>>) : RecyclerView.Adapter<BirthdayAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val birthdayName: TextView = itemView.findViewById(R.id.birthdayName)
        val birthdayDay: TextView = itemView.findViewById(R.id.birthdayDay)
        val birthdayMonth: TextView = itemView.findViewById(R.id.birthdayMonth)
        val birthdayDeleteButton: Button = itemView.findViewById(R.id.birthdayDeleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_birthday, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        birthdays.value?.let { birthdayList ->
            val birthday = birthdayList[position]
            holder.birthdayName.text = birthday.name
            holder.birthdayDay.text = birthday.day.toString()
            holder.birthdayMonth.text = birthday.month.toString()

            holder.birthdayDeleteButton.setOnClickListener {
                //TODO
            }
        }
    }

    override fun getItemCount(): Int {
        return birthdays.value?.size ?: 0
    }
}
