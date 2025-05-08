package com.unab.pharmaliveapp

import Producto
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicamentoAdapter(
    private val medicamentoList: List<Producto>,
    private val onEditClick: (Producto) -> Unit
) : RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder>() {

    // ViewHolder que mantiene las referencias a las vistas del item
    inner class MedicamentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreMedicamento)  // Nombre del medicamento
        val ivEditar: ImageView = view.findViewById(R.id.ivEditarMedicamento)  // Icono de editar
    }

    // Se llama cuando se crea un nuevo item en la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medicamento, parent, false)
        return MedicamentoViewHolder(view)
    }

    // Se llama para enlazar los datos de un medicamento a las vistas del item
    override fun onBindViewHolder(holder: MedicamentoViewHolder, position: Int) {
        val medicamento = medicamentoList[position]

        // Asignamos el nombre del medicamento al TextView
        holder.tvNombre.text = medicamento.nombre

        // Configuramos el clic del icono de editar
        holder.ivEditar.setOnClickListener {
            onEditClick(medicamento)  // Llamamos a la función de edición pasada en el constructor
        }
    }

    // Devuelve el número total de items en la lista
    override fun getItemCount(): Int {
        return medicamentoList.size
    }
}
