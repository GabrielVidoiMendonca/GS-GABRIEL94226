package com.example.gabrielmendonca_rm94226

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gabrielmendonca_rm94226.adapter.DicaAdapter
import com.example.gabrielmendonca_rm94226.data.Dica
import com.example.gabrielmendonca_rm94226.data.DicasDBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DicaAdapter
    private lateinit var dbHelper: DicasDBHelper
    private lateinit var dicas: MutableList<Dica>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listaDicas = listOf(
            Dica(1,"Desligue aparelhos que não estão em uso", "Aparelhos eletrônicos consomem energia mesmo em modo de espera. Desconecte quando não for usar."
            ),
            Dica(2,"Use lâmpadas LED", "As lâmpadas LED são mais eficientes e duram mais do que as incandescentes."
            ),
            Dica(3,"Aproveite a luz natural", "Abra as cortinas e janelas para iluminar a casa durante o dia, economizando energia elétrica."
            ),
            Dica(4,"Desligue luzes ao sair", "Certifique-se de apagar as luzes ao sair de um cômodo para evitar desperdício de energia."
            ),
            Dica(5,"Use eletrodomésticos eficientes", "Escolha aparelhos com selo de eficiência energética para reduzir o consumo."
            ),
            Dica(6,"Reduza o uso do ar-condicionado", "Mantenha janelas e portas fechadas ao usar o ar-condicionado e ajuste a temperatura para evitar desperdício."
            )
        )
        dbHelper = DicasDBHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        dicas = dbHelper.getAllDicas().toMutableList()
        adapter = DicaAdapter(listaDicas)
        recyclerView.adapter = adapter

        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        dicas.clear()
        dicas.addAll(dbHelper.getAllDicas())
        adapter.notifyDataSetChanged()
    }
}