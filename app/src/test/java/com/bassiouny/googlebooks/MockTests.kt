package com.bassiouny.googlebooks

import com.bassiouny.googlebooks.model.BooksResponse
import com.bassiouny.googlebooks.model.Item
import com.bassiouny.googlebooks.network.BooksApi
import com.bassiouny.googlebooks.ui.booklist.BooksPresenter
import com.bassiouny.googlebooks.ui.booklist.BooksView
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class MockingTests {

    private val api: BooksApi = mock()
    private lateinit var testScheduler: TestScheduler
    private lateinit var presenter: BooksPresenter
    private val view: BooksView = mock()


    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        presenter = BooksPresenter(view, testScheduler, testScheduler, api)
    }

    @Test
    fun test_getBooks_should_callSuccess() {
        val mockedResponse: BooksResponse = mock()
        val searchKey = "flowers"

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .search(searchKey)

        presenter.fetchBooks(searchKey)

        testScheduler.triggerActions()

        verify(view).updateList(mockedResponse)
    }

    @Test
    fun test_getBooks_shouldNot_callNoResult() {
        val mockedResponse: BooksResponse = mock()
        val items = ArrayList<Item>()
        val searchKey = "flowers"

        items.add(mock(Item::class.java))

        `when`(mockedResponse.items).thenReturn(items)

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .search(searchKey)

        presenter.fetchBooks(searchKey)

        testScheduler.triggerActions()

        verify(view, times(0)).noResults()
    }

    @Test
    fun test_getBooks_should_callNoResult() {
        val mockedResponse: BooksResponse = mock()
        val items = ArrayList<Item>()
        val searchKey = "test"

        `when`(mockedResponse.items).thenReturn(items)

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .search(searchKey)

        presenter.fetchBooks(searchKey)

        testScheduler.triggerActions()

        verify(view).noResults()
    }

    @Test
    fun test_getBooks_should_callError() {
        val mockedResponse: Throwable = mock()
        val searchKey = "test"

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .search(searchKey)

        presenter.fetchBooks(searchKey)

        testScheduler.triggerActions()

        verify(view).showLoading()
        verify(view).showError("error")
        verify(view).hideLoading()
    }
}