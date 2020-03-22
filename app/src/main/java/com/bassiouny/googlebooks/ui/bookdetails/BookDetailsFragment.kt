package com.bassiouny.googlebooks.ui.bookdetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bassiouny.googlebooks.R
import com.bassiouny.googlebooks.base.BaseFragment
import com.bassiouny.googlebooks.network.Client
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_book_details.*

class BookDetailsFragment : BaseFragment<BookDetailsPresenter>(),
    BookDetailsView {
    private var client = Client()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun instantiatePresenter(): BookDetailsPresenter {
        return BookDetailsPresenter(this, AndroidSchedulers.mainThread(),
            Schedulers.io(),
            client.getService())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences: SharedPreferences? = context?.getSharedPreferences("default", Context.MODE_PRIVATE)
        val id = sharedPreferences?.getString("bookId", "nothing here")
        presenter.fetchBookDetails(id!!)
    }

    override fun showDescription(description: String) {
        test.text = description
    }

}
