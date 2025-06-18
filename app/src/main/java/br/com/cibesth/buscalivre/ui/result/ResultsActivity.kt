package br.com.cibesth.buscalivre.ui.result

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cibesth.buscalivre.R
import br.com.cibesth.buscalivre.data.repository.ProductRepository
import br.com.cibesth.buscalivre.ui.details.DetailActivity

class ResultsActivity : AppCompatActivity() {

    private lateinit var viewModel: ResultsViewModel
    private lateinit var adapter: ResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val category = intent.getStringExtra("category") ?: run {
            showErrorAndFinish()
            return
        }

        val repository = ProductRepository(this)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ResultsViewModel(repository) as T
            }
        })[ResultsViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.rvResults)
        adapter = ResultsAdapter { item ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("category", category)
            intent.putExtra("productId", item.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.items.observe(this) { list ->
            adapter.submitList(list)
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }

        viewModel.search(category)
    }

    private fun showErrorAndFinish() {
        Toast.makeText(this, "Erro: Categoria inválida!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
