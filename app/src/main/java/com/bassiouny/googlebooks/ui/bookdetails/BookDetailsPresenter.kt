package com.bassiouny.googlebooks.ui.bookdetails

import com.bassiouny.googlebooks.base.BasePresenter
import com.bassiouny.googlebooks.network.BooksApi
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class BookDetailsPresenter(
    bookDetailsView: BookDetailsView,
    private val mainThread: Scheduler,
    private val io: Scheduler,
    private val api: BooksApi
) :
    BasePresenter<BookDetailsView>(bookDetailsView) {
    private var subscription: Disposable? = null
    private val bookDetailsInteractor = BookDetailsInteractor()

    fun fetchBookDetails(bookId: String) {
        if (subscription == null) {
            subscription =
                bookDetailsInteractor.getBookDetails(mainThread, io, bookId, api)
                    ?.subscribe(
                        { bookDetailsResponse ->
                            view.showDescription(bookDetailsResponse.volumeInfo.description)
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

