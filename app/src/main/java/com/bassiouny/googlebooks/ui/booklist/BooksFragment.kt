package com.bassiouny.googlebooks.ui.booklist

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bassiouny.googlebooks.R
import com.bassiouny.googlebooks.base.BaseFragment
import com.bassiouny.googlebooks.model.BooksResponse
import com.bassiouny.googlebooks.model.Item
import com.bassiouny.googlebooks.network.Client
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
            viewAdapter = RepoAdapter(fetchedBooks, it!!)
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


}
