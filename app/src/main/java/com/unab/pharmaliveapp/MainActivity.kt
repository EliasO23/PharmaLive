package com.unab.pharmaliveapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity() {

    private lateinit var btnInicio: LinearLayout
    private lateinit var ivInicio: ImageView
    private lateinit var tvInicio: TextView
    private lateinit var btnBuscar: LinearLayout
    private lateinit var ivBuscar: ImageView
    private lateinit var tvBuscar: TextView
    private lateinit var btnMedicina: LinearLayout
    private lateinit var ivMedicina: ImageView
    private lateinit var tvMedicina: TextView
    private lateinit var btnPerfil: LinearLayout
    private lateinit var ivPerfil: ImageView
    private lateinit var tvPerfil: TextView

    private lateinit var btnAgregar: LinearLayout
    private lateinit var btnEditar: LinearLayout
    private lateinit var btnEliminar: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName = currentUser?.displayName ?: "Usuario"

        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        tvUserName.text = userName

        btnInicio = findViewById(R.id.btnInicio)
        ivInicio = findViewById(R.id.ivInicio)
        tvInicio = findViewById(R.id.tvInicio)
        btnBuscar = findViewById(R.id.btnBuscar)
        ivBuscar = findViewById(R.id.ivBuscar)
        tvBuscar = findViewById(R.id.tvBuscar)
        btnMedicina = findViewById(R.id.btnMedicina)
        ivMedicina = findViewById(R.id.ivMedicina)
        tvMedicina = findViewById(R.id.tvMedicina)
        btnPerfil = findViewById(R.id.btnPerfil)
        ivPerfil = findViewById(R.id.ivPerfil)
        tvPerfil = findViewById(R.id.tvPerfil)

        btnAgregar = findViewById(R.id.btnAgregar)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)

        // Activar la sección actual del menú
        actualizarMenuActivo(
            ivInicio, tvInicio,
            ivBuscar, tvBuscar,
            ivMedicina, tvMedicina,
            ivPerfil, tvPerfil,
            "inicio"
        )


        btnMedicina.setOnClickListener {
            startActivity(Intent(this, MedicineActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btnBuscar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btnPerfil.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btnAgregar.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btnEditar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btnEliminar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


    }
}