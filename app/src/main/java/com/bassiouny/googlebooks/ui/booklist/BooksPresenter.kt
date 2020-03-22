package com.bassiouny.googlebooks.ui.booklist

import com.bassiouny.googlebooks.base.BasePresenter
import com.bassiouny.googlebooks.network.BooksApi
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class BooksPresenter(
    booksView: BooksView,
    private val mainThread: Scheduler,
    private val io: Scheduler,
    private val api: BooksApi
) : BasePresenter<BooksView>(booksView) {
    private var subscription: Disposable? = null
    private val booksInteractor = BooksInteractor()

    override fun onViewCreated() {
        fetchBooks("flowers")
    }

    fun fetchBooks(searchText: String) {
        if (subscription == null) {
            view.showLoading()
            subscription =
                booksInteractor.getBooks(mainThread, io, searchText, api)
                    ?.subscribe(
                        { booksResponse ->
                            view.updateList(booksResponse)
                            if (booksResponse.items == null || booksResponse.items.isEmpty()) {
                                view.noResults()
                            }
                            view.hideLoading()
                            subscription = null
                        },
                        {
                            view.showError("error")
                            view.hideLoading()
                            subscription = null
                        }
                    )
        }
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}
