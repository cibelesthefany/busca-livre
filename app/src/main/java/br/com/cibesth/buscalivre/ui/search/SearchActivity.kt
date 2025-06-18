package br.com.cibesth.buscalivre.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.cibesth.buscalivre.R
import br.com.cibesth.buscalivre.ui.result.ResultsActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvError: TextView

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        tvError = findViewById(R.id.tvError)

        viewModel.isButtonEnabled.observe(this, Observer { isEnabled ->
            btnSearch.isEnabled = isEnabled
            btnSearch.alpha = if (isEnabled) 1.0f else 0.5f
        })

        viewModel.error.observe(this, Observer { errorMsg ->
            if (errorMsg != null) {
                tvError.text = errorMsg
                tvError.visibility = View.VISIBLE
            } else {
                tvError.visibility = View.GONE
            }
        })

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onQueryChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.onQueryChanged(etSearch.text.toString())

        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            val category = viewModel.validateQuery(query)

            if (category != null) {
                val intent = Intent(this, ResultsActivity::class.java)
                intent.putExtra("category", category)
                startActivity(intent)
            }
        }
    }
}
