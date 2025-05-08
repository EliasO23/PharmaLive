package com.unab.pharmaliveapp

import Producto
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MedicineActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoList: MutableList<Producto>
    private lateinit var adapter: ProductoAdapter
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_medicine)
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

        tvTitulo.text = "Medicina"
        btnAtras.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //Lista medicinas
        recyclerView = findViewById(R.id.recyclerProductos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productoList = mutableListOf()
        adapter = ProductoAdapter(productoList)
        recyclerView.adapter = adapter

        dbRef = FirebaseDatabase.getInstance().getReference("productos")

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

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productoList.clear()
                for (data in snapshot.children) {
                    val producto = data.getValue(Producto::class.java)
                    producto?.let { productoList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejo de errores
            }
        })


    }
}