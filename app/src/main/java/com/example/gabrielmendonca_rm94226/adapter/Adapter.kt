package com.example.gabrielmendonca_rm94226.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gabrielmendonca_rm94226.data.Dica
import android.widget.Filter
import android.widget.Filterable
import com.example.gabrielmendonca_rm94226.R

class DicaAdapter(private var dicas: List<Dica>) : RecyclerView.Adapter<DicaAdapter.DicaViewHolder>(), Filterable {

    private var dicasFiltered: List<Dica> = dicas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = dicasFiltered[position]
        holder.tituloTextView.text = dica.titulo
        holder.descricaoTextView.text = dica.descricao

        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, dica.descricao, Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int = dicasFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: MutableList<Dica> = mutableListOf()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(dicas)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (dica in dicas) {
                        if (dica.titulo.toLowerCase().contains(filterPattern)) {
                            filteredList.add(dica)
                        }
                    }
                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dicasFiltered = results?.values as List<Dica>
                notifyDataSetChanged()
            }
        }
    }

    inner class DicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloTextView: TextView = itemView.findViewById(R.id.tituloDica)
        val descricaoTextView: TextView = itemView.findViewById(R.id.descricaoDica)
    }
}