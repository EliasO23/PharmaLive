package com.unab.pharmaliveapp

import Producto
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.core.widget.addTextChangedListener


class SearchActivity : BaseActivity() {

    private lateinit var productoList: List<Producto> // Lista de productos que mostrarás en la búsqueda
    private lateinit var rvResultados: RecyclerView
    private lateinit var etBuscar: EditText
    private lateinit var medicamentoAdapter: MedicamentoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

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

        tvTitulo.text = "Buscar"
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
            "buscar"
        )

        btnInicio.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnBuscar.setOnClickListener {

        }

        btnPerfil.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnMedicina.setOnClickListener {
            startActivity(Intent(this, MedicineActivity::class.java))
        }

        rvResultados = findViewById(R.id.rvResultados)
        etBuscar = findViewById(R.id.etBuscar)

        // Configura el RecyclerView
        rvResultados.layoutManager = LinearLayoutManager(this)
        medicamentoAdapter = MedicamentoAdapter(emptyList()) { producto ->
            // Acción al hacer clic en el ícono de editar
            val intent = Intent(this, EditMedicineActivity::class.java)
            intent.putExtra("productoId", producto.id)
            startActivity(intent)
        }
        rvResultados.adapter = medicamentoAdapter

        // Configura la barra de búsqueda para detectar cambios
        etBuscar.addTextChangedListener {
            val query = it.toString()
            realizarBusqueda(query)
        }

        // Cargar productos desde la base de datos en tiempo real
        cargarProductos()

    }

    private fun cargarProductos() {
        val database = FirebaseDatabase.getInstance().reference
        database.child("productos").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productoList = mutableListOf()
                for (dataSnapshot in snapshot.children) {
                    val producto = dataSnapshot.getValue(Producto::class.java)
                    if (producto != null) {
                        (productoList as MutableList).add(producto)
                    }
                }
                // Actualiza el adaptador con la lista completa
                medicamentoAdapter = MedicamentoAdapter(productoList) { producto ->
                    val intent = Intent(this@SearchActivity, EditMedicineActivity::class.java)
                    intent.putExtra("productoId", producto.id)
                    startActivity(intent)
                }
                rvResultados.adapter = medicamentoAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores
            }
        })
    }

    private fun realizarBusqueda(query: String) {
        val resultadoBusqueda = productoList.filter { it.nombre.contains(query, ignoreCase = true) }
        medicamentoAdapter = MedicamentoAdapter(resultadoBusqueda) { producto ->
            val intent = Intent(this, EditMedicineActivity::class.java)
            intent.putExtra("productoId", producto.id)
            startActivity(intent)
        }
        rvResultados.adapter = medicamentoAdapter
    }
}