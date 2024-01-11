package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.jesuspeirolopez.myflexagenda.databinding.ActivityEventCreateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventCreateBinding

    private lateinit var agendaDatabase: AgendaDatabase

    private val SELECT_PICTURE = 200

    private lateinit var imageChooserLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageChooserLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            onActivityResult(result.resultCode, result.data)
        }

        agendaDatabase = Room.databaseBuilder(
            applicationContext,
            AgendaDatabase::class.java, "agenda-database"
        ).build()

        /*
        var selectedImageUri: Uri? = null

        val imageChooserLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data


                Log.d("ImageURI", "Image URI: $selectedImageUri")
            }
        }
         */

        //Longitud máxima para las horas
        binding.eventCreateTextTime1.filters = arrayOf(InputFilter.LengthFilter(5))
        binding.eventCreateTextTime2.filters = arrayOf(InputFilter.LengthFilter(5))


        binding.eventCreateBack.setOnClickListener {
            val intent = Intent(this@EventCreateActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        binding.addEventButton.setOnClickListener {
            val title = binding.eventTitleInsert.text.toString()
            val description = binding.eventDescriptionInsert.text.toString()
            val startTime = binding.eventCreateTextTime1.text.toString()
            val endTime = binding.eventCreateTextTime2.text.toString()
            val day = binding.eventCreateDay1.text.toString().toInt()
            val month = getMonthNumber(binding.eventCreateDay3.text.toString())
            val year = binding.eventCreateYear.text.toString().toInt()

            val imagePath = binding.imageInsert.toString()

            val event = EventMO(
                title = title,
                description = description,
                startTime = startTime,
                endTime = endTime,
                day = day,
                month = month,
                year = year,
                imagePath = imagePath
            )

            if (startTime == "" || endTime == "" || title == "") {
                //Toast por si dejas campos vacíos en las horas o el título
                Toast.makeText(
                    this@EventCreateActivity,
                    "Error: No dejes campos vacíos",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (
                //Expresión regular para que las horas sigan un patrón correcto
                !binding.eventCreateTextTime1.text.matches(Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]\$"))
                || !binding.eventCreateTextTime2.text.matches(Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]\$"))
            ) {
                //Toast por si no siguen las horas el patrón
                Toast.makeText(
                    this@EventCreateActivity,
                    "Error: Formato de hora incorrecto",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                //Esto divide el start time y el endtime en dos partes, la de antes del : y la de después
                val startTimeParts = startTime.split(":")
                val endTimeParts = endTime.split(":")

                //Guardamos en hora y minuto cada uno para compararlos
                val startHour = startTimeParts[0].toInt()
                val startMinute = startTimeParts[1].toInt()

                val endHour = endTimeParts[0].toInt()
                val endMinute = endTimeParts[1].toInt()

                //Comprobacion para comprobar que la hora final no sea antes de la inicial, pero puede ser igual
                if (startHour > endHour || (startHour == endHour && startMinute > endMinute)) {
                    Toast.makeText(
                        this@EventCreateActivity,
                        "Error: La hora final debe ser posterior a la inicial",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Corrutina para insertar el evento y actualizar en el recyclerview
                    CoroutineScope(Dispatchers.IO).launch {
                        agendaDatabase.eventDao().insertEvent(event)
                    }

                    //Toast para indicar que el evento se ha guardado
                    Toast.makeText(this@EventCreateActivity, "Evento guardado", Toast.LENGTH_SHORT)
                        .show()

                    //Con esto volvemos de nuevo a la pantalla principal
                    val intent = Intent(this@EventCreateActivity, MainActivity::class.java)
                    startActivity(intent)

                    finish()
                }

            }

        }

        //Método que actualiza en tiempo real la interfaz para elegir la imagen
        binding.addImageText.setOnClickListener {
            runOnUiThread {
                imageChooser()
            }

        }

        //Para poner el dia, mes y año que vengan de la actividad principal
        binding.eventCreateDay1.text = intent.getStringExtra("day")
        binding.eventCreateDay3.text = intent.getStringExtra("month")
        binding.eventCreateYear.text = intent.getStringExtra("year")


    }

    //Método para convertir el nombre de un mes a su respectivo numero
    fun getMonthNumber(nombreMes: String): Int {
        return when (nombreMes.lowercase()) {
            "enero" -> 1
            "febrero" -> 2
            "marzo" -> 3
            "abril" -> 4
            "mayo" -> 5
            "junio" -> 6
            "julio" -> 7
            "agosto" -> 8
            "septiembre" -> 9
            "octubre" -> 10
            "noviembre" -> 11
            "diciembre" -> 12
            else -> throw IllegalArgumentException("Nombre del mes no válido: $nombreMes")
        }
    }

    //Método para elegir la imagen
    private fun imageChooser() {

        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        imageChooserLauncher.launch(Intent.createChooser(i, "Select Picture"))
    }

    //Otra parte del método para añadir la imagen
    private fun onActivityResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {

            if (resultCode == SELECT_PICTURE) {

                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {

                    binding.imageInsert.setImageURI(selectedImageUri)
                }
            }
        }
    }


}