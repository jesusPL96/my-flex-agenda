package com.jesuspeirolopez.myflexagenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val events: LiveData<List<EventMO>>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {


    //Esto no es óptimo ni seguro pero funciona de alguna forma
    init {
        events.observeForever { notifyDataSetChanged() }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val hourTextView: TextView = itemView.findViewById(R.id.hourTextView)
        val minuteTextView: TextView = itemView.findViewById(R.id.minuteTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events.value?.get(position)
        event?.let {
            holder.titleTextView.text = it.title
            // En verdad solo necesito el startTime que es hour
            holder.hourTextView.text = it.startTime
        }
    }

    override fun getItemCount(): Int {
        return events.value?.size ?: 0
    }
}
