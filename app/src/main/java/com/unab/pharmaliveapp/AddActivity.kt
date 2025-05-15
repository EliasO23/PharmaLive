package com.unab.pharmaliveapp

import Producto
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddActivity : BaseActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        database = FirebaseDatabase.getInstance().getReference("productos")

        // Referencias
        val ivInicio = findViewById<ImageView>(R.id.ivInicio)
        val tvInicio = findViewById<TextView>(R.id.tvInicio)
        val ivBuscar = findViewById<ImageView>(R.id.ivBuscar)
        val tvBuscar = findViewById<TextView>(R.id.tvBuscar)
        val ivMedicina = findViewById<ImageView>(R.id.ivMedicina)
        val tvMedicina = findViewById<TextView>(R.id.tvMedicina)
        val ivPerfil = findViewById<ImageView>(R.id.ivPerfil)
        val tvPerfil = findViewById<TextView>(R.id.tvPerfil)

        val btnInicio = findViewById<LinearLayout>(R.id.btnInicio)
        val btnBuscar = findViewById<LinearLayout>(R.id.btnBuscar)
        val btnMedicina = findViewById<LinearLayout>(R.id.btnMedicina)
        val btnPerfil = findViewById<LinearLayout>(R.id.btnPerfil)

        val btnAtras = findViewById<ImageView>(R.id.btnAtras)
        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)

        tvTitulo.text = "Agregar"
        btnAtras.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //Form
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etCantidad = findViewById<EditText>(R.id.etCantidad)
        val spinnerLaboratorio = findViewById<Spinner>(R.id.spinnerLaboratorio)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Opciones para el Spinner
        val laboratorios = resources.getStringArray(R.array.laboratorios)
        val listaConOpcionInicial = listOf("Selecciona un laboratorio") + laboratorios

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaConOpcionInicial)
        spinnerLaboratorio.adapter = adapter


        // Actualizar colores
        actualizarMenuActivo(
            ivInicio, tvInicio,
            ivBuscar, tvBuscar,
            ivMedicina, tvMedicina,
            ivPerfil, tvPerfil,
            "medicina"
        )

        btnInicio.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnBuscar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        btnPerfil.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnMedicina.setOnClickListener {
            startActivity(Intent(this, MedicineActivity::class.java))
        }

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val descripcion = etDescripcion.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull()
            val cantidad = etCantidad.text.toString().toIntOrNull()
            val laboratorio = spinnerLaboratorio.selectedItem.toString()

            if (nombre.isBlank() || descripcion.isBlank() || precio == null || cantidad == null || laboratorio == "Selecciona un laboratorio") {
                Toast.makeText(this, "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = database.push().key ?: return@setOnClickListener

            val producto = Producto(
                id = id,
                nombre = nombre,
                descripcion = descripcion,
                precio = precio,
                cantidad = cantidad,
                laboratorio = laboratorio
            )

            database.child(id).setValue(producto).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Producto guardado exitosamente", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la pantalla
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}