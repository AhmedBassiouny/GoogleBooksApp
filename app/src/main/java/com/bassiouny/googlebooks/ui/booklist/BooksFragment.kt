package com.bassiouny.googlebooks.ui.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bassiouny.googlebooks.R
import com.bassiouny.googlebooks.base.BaseFragment
import com.bassiouny.googlebooks.network.Client
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BooksFragment : BaseFragment<BooksPresenter>(), BooksView {
    private lateinit var recyclerView: RecyclerView
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
        presenter.onViewCreated()
    }

    private fun setupList() {

    }

    override fun instantiatePresenter(): BooksPresenter {
        return BooksPresenter(this,
            AndroidSchedulers.mainThread(),
            Schedulers.io(),
            client.getService())
    }

}
