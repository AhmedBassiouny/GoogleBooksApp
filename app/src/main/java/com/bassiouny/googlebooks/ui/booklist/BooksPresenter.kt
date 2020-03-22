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
            subscription =
                booksInteractor.getBooks(mainThread, io, searchText, api)
                    ?.subscribe(
                        { booksResponse ->
                            view.updateList(booksResponse)
                            if (booksResponse.items == null || booksResponse.items.isEmpty()) {
                                // TODO: show no books view
                            }
                            subscription = null
                        },
                        {
                            // TODO: handel error
                            subscription = null
                        }
                    )
        }
    }

    fun fetchBookDetails(bookId: String) {
        if (subscription == null) {
            subscription =
                booksInteractor.getBookDetails(mainThread, io, bookId, api)
                    ?.subscribe(
                        { bookDetailsResponse ->
                            if (bookDetailsResponse.id == "" || bookDetailsResponse.id.isEmpty()) {
                                // TODO: show no books view
                            }
                            subscription = null
                        },
                        {
                            // TODO: handel error
                            subscription = null
                        }
                    )
        }
    }
}
