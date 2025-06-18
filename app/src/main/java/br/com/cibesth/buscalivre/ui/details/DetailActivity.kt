package br.com.cibesth.buscalivre.ui.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.cibesth.buscalivre.R
import br.com.cibesth.buscalivre.data.repository.ProductRepository
import br.com.cibesth.buscalivre.utils.fixPrice
import br.com.cibesth.buscalivre.utils.toBrazilianCurrency
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val category = intent.getStringExtra("category") ?: return showErrorAndFinish()
        val productId = intent.getStringExtra("productId") ?: return showErrorAndFinish()

        val repository = ProductRepository(this)
        viewModel = ViewModelProvider(
            this,
            DetailViewModelFactory(repository)
        )[DetailViewModel::class.java]

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val tvCategory = findViewById<TextView>(R.id.tvCategory)
        val ivImage = findViewById<ImageView>(R.id.ivImage)
        val tvError = findViewById<TextView>(R.id.tvError)

        viewModel.loadProductDetails(category, productId)

        viewModel.itemDetail.observe(this) { item ->
            if (item != null) {
                tvTitle.text = item.title
                tvPrice.text = item.price.fixPrice().toBrazilianCurrency()

                val imageUrl = item.pictures?.firstOrNull()?.url ?: item.thumbnail
                Glide.with(this).load(imageUrl).into(ivImage)

                tvError.visibility = View.GONE
                showContent(tvTitle, tvPrice, tvDescription, tvCategory, ivImage)
            } else {
                showError(tvError, tvTitle, tvPrice, tvDescription, tvCategory, ivImage)
            }
        }

        viewModel.description.observe(this) { desc ->
            if (desc != null) {
                tvDescription.text = desc.plain_text
            }
        }

        viewModel.category.observe(this) { cat ->
            if (cat != null) {
                val breadcrumb = cat.path_from_root.joinToString(" > ") { it.name }
                tvCategory.text = breadcrumb
            }
        }

        viewModel.error.observe(this) {
            showError(tvError, tvTitle, tvPrice, tvDescription, tvCategory, ivImage)
            tvError.text = it ?: "Ops, não estamos encontrando esse produto."
        }
    }

    private fun showError(
        tvError: TextView,
        vararg views: View
    ) {
        tvError.visibility = View.VISIBLE
        views.forEach { it.visibility = View.GONE }
    }

    private fun showContent(
        vararg views: View
    ) {
        views.forEach { it.visibility = View.VISIBLE }
    }

    private fun showErrorAndFinish() {
        finish()
    }
}
