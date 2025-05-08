package com.unab.pharmaliveapp

import Producto
import android.app.AlertDialog
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
import com.google.firebase.database.FirebaseDatabase

class EditMedicineActivity : BaseActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etCantidad: EditText
    private lateinit var spinnerLaboratorio: Spinner
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminarMedicamento: Button

    private var productoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_edit_medicine)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        // Referencias a los campos
        etNombre = findViewById(R.id.etNombre)
        etDescripcion = findViewById(R.id.etDescripcion)
        etPrecio = findViewById(R.id.etPrecio)
        etCantidad = findViewById(R.id.etCantidad)
        spinnerLaboratorio = findViewById(R.id.spinnerLaboratorio)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminarMedicamento = findViewById(R.id.btnEliminarMedicamento)

        productoId = intent.getStringExtra("productoId")

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

        tvTitulo.text = "Editar"
        btnAtras.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

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

        }

        cargarDatosProducto()

        btnActualizar.setOnClickListener {
            guardarCambios()
        }

        btnEliminarMedicamento.setOnClickListener {
            eliminarProducto()
        }

    }

    private fun cargarDatosProducto() {
        val dbRef = FirebaseDatabase.getInstance().getReference("productos").child(productoId!!)
        dbRef.get().addOnSuccessListener { snapshot ->
            val producto = snapshot.getValue(Producto::class.java)
            if (producto != null) {
                etNombre.setText(producto.nombre)
                etDescripcion.setText(producto.descripcion)
                etPrecio.setText(producto.precio.toString())
                etCantidad.setText(producto.cantidad.toString())

                // Cargar directamente desde el string-array sin opción extra
                val laboratorioList = resources.getStringArray(R.array.laboratorios)
                val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, laboratorioList)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerLaboratorio.adapter = spinnerAdapter

                // Asignar selección
                val index = laboratorioList.indexOf(producto.laboratorio)
                if (index >= 0) spinnerLaboratorio.setSelection(index)
            }
        }
    }


    private fun guardarCambios() {
        val nombre = etNombre.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etPrecio.text.toString().toDoubleOrNull() ?: 0.0
        val cantidad = etCantidad.text.toString().toIntOrNull() ?: 0
        val laboratorio = spinnerLaboratorio.selectedItem.toString()

        val productoActualizado = Producto(
            id = productoId ?: "",
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            cantidad = cantidad,
            laboratorio = laboratorio
        )

        FirebaseDatabase.getInstance().getReference("productos")
            .child(productoId!!)
            .setValue(productoActualizado)
            .addOnSuccessListener {
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
    }

    private fun eliminarProducto() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro que deseas eliminar este medicamento?")
            .setPositiveButton("Sí") { _, _ ->
                FirebaseDatabase.getInstance().getReference("productos")
                    .child(productoId!!) // Asegúrate que no sea null
                    .removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.red)) // o usa Color.RED
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.primaryColor))
        }

        dialog.show()
    }


}