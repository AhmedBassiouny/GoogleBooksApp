package com.bassiouny.googlebooks.ui.booklist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bassiouny.googlebooks.R
import com.bassiouny.googlebooks.base.BaseFragment
import com.bassiouny.googlebooks.model.BooksResponse
import com.bassiouny.googlebooks.model.Item
import com.bassiouny.googlebooks.network.Client
import com.bassiouny.googlebooks.ui.bookdetails.BookDetailsActivity
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_books.*

class BooksFragment : BaseFragment<BooksPresenter>(),
    BooksView,
    MaterialSearchBar.OnSearchActionListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RepoAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var fetchedBooks = ArrayList<Item>()
    private var client = Client()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        search_bar.setOnSearchActionListener(this)
        presenter.onViewCreated()
    }

    private fun setupList() {
        context.let {
            viewManager = LinearLayoutManager(it)
            viewAdapter = RepoAdapter(fetchedBooks, it!!) { position -> itemClicked(position) }
        }

        recyclerView = books.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun updateList(booksResponse: BooksResponse?) {
        fetchedBooks = booksResponse?.items as ArrayList<Item>
        viewAdapter.updateData(fetchedBooks)
    }

    override fun noResults() {
        Toast.makeText(context, "lk", Toast.LENGTH_LONG).show()
    }

    override fun instantiatePresenter(): BooksPresenter {
        return BooksPresenter(
            this,
            AndroidSchedulers.mainThread(),
            Schedulers.io(),
            client.getService()
        )
    }

    override fun onButtonClicked(buttonCode: Int) {
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        presenter.fetchBooks(search_bar.text.toString())
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        presenter.fetchBooks(search_bar.text.toString())
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity?.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun itemClicked(position: Int) {
        Toast.makeText(context, "Clicked: $position", Toast.LENGTH_SHORT).show()
        val sharedPreferences: SharedPreferences? =
            context?.getSharedPreferences("default", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.putString("bookId", fetchedBooks[position].id)
        editor?.apply()
        startActivity(Intent(context, BookDetailsActivity::class.java))
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
