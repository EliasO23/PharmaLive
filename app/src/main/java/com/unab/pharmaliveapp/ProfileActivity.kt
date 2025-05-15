package com.unab.pharmaliveapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var ivProfile: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        tvUsername = findViewById(R.id.tvProfileUsername)
        tvEmail = findViewById(R.id.tvProfileEmail)
        ivProfile = findViewById(R.id.ivProfileImage)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            auth.signOut() // Cierra la sesión en Firebase

            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }


        user?.let {
            tvEmail.text = it.email
            tvUsername.text = it.displayName ?: "Usuario sin nombre"
            // Puedes cargar una imagen desde URL si guardaste una en el perfil
            // Glide.with(this).load(it.photoUrl).into(ivProfile)
        }

        val imageView = findViewById<CircleImageView>(R.id.ivProfileImage)
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.primaryColor))


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

        tvTitulo.text = "Perfil"
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
            "perfil"
        )

        btnInicio.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnBuscar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        btnPerfil.setOnClickListener {

        }

        btnMedicina.setOnClickListener {
            startActivity(Intent(this, MedicineActivity::class.java))
        }

    }
}