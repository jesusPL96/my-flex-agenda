package com.jesuspeirolopez.myflexagenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Inicializa las vistas dentro de cada elemento de la cuadrícula
        // Por ejemplo: val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflar el diseño de un elemento de la cuadrícula
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Configurar las vistas con datos específicos del evento
        val event = events[position]
        // Por ejemplo: holder.titleTextView.text = event.title
    }

    override fun getItemCount(): Int {
        return events.size
    }
}
