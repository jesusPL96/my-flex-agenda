package com.jesuspeirolopez.myflexagenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

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

        val event = events[position]
        holder.titleTextView.text = event.title
        //En verdad solo necesito el startTime que es hour
        holder.hourTextView.text = event.startTime
        //holder.minuteTextView.text = event.endTime
    }

    override fun getItemCount(): Int {
        return events.size
    }
}
