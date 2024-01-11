package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventAdapter(private val events: LiveData<List<EventMO>>, private val eventViewModel: EventViewModel) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {


    //Para actualizar los datos al actualizarlos en el recycler
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

        //Asignacion de los valores y el listener en el boton a cada item evento
        val event = events.value?.get(position)
        event?.let {
            holder.titleTextView.text = it.title

            holder.hourTextView.text = it.startTime

            holder.minuteTextView.text= it.id.toString()
            holder.deleteButton.setOnClickListener {
                val eventId = event.id


                CoroutineScope(Dispatchers.IO).launch {
                    eventViewModel.deleteEventById(eventId)
                }

                GlobalScope.launch(Dispatchers.Main) {
                    notifyItemRemoved(position)
                }

            }

            holder.itemView.setOnClickListener{
                val context = holder.itemView.context
                val intent = Intent(context, EventInfoActivity::class.java)
                intent.putExtra("eventId", event.id)
                context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        return events.value?.size ?: 0
    }
}
