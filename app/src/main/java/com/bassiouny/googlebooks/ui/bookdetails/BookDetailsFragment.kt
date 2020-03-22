package com.bassiouny.googlebooks.ui.bookdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bassiouny.googlebooks.R
import com.bassiouny.googlebooks.base.BaseFragment

class BookDetailsFragment : BaseFragment<BookDetailsPresenter>(),
    BookDetailsView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun instantiatePresenter(): BookDetailsPresenter {
        return BookDetailsPresenter(this)
    }

}
