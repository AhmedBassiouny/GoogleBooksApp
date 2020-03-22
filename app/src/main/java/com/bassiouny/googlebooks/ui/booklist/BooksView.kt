package com.bassiouny.googlebooks.ui.booklist

import com.bassiouny.googlebooks.base.BaseView
import com.bassiouny.googlebooks.model.BooksResponse

interface BooksView : BaseView {
    fun updateList(booksResponse: BooksResponse?)
    fun noResults()
    fun showError(error: String)
    fun showLoading()
    fun hideLoading()

}
