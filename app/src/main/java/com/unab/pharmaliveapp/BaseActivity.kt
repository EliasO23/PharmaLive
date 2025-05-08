package com.unab.pharmaliveapp

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

open class BaseActivity : AppCompatActivity() {
    protected fun actualizarMenuActivo(
        ivInicio: ImageView, tvInicio: TextView,
        ivBuscar: ImageView, tvBuscar: TextView,
        ivMedicina: ImageView, tvMedicina: TextView,
        ivPerfil: ImageView, tvPerfil: TextView,
        activo: String
    ) {
        val colorActivo = ContextCompat.getColor(this, R.color.secondColor)
        val colorInactivo = ContextCompat.getColor(this, R.color.grayligth)

        // Todos inactivos
        ivInicio.setColorFilter(colorInactivo)
        tvInicio.setTextColor(colorInactivo)
        ivBuscar.setColorFilter(colorInactivo)
        tvBuscar.setTextColor(colorInactivo)
        ivMedicina.setColorFilter(colorInactivo)
        tvMedicina.setTextColor(colorInactivo)
        ivPerfil.setColorFilter(colorInactivo)
        tvPerfil.setTextColor(colorInactivo)

        // Activar uno
        when (activo) {
            "inicio" -> {
                ivInicio.setColorFilter(colorActivo)
                tvInicio.setTextColor(colorActivo)
            }
            "buscar" -> {
                ivBuscar.setColorFilter(colorActivo)
                tvBuscar.setTextColor(colorActivo)
            }
            "medicina" -> {
                ivMedicina.setColorFilter(colorActivo)
                tvMedicina.setTextColor(colorActivo)
            }
            "perfil" -> {
                ivPerfil.setColorFilter(colorActivo)
                tvPerfil.setTextColor(colorActivo)
            }
        }
    }
}
