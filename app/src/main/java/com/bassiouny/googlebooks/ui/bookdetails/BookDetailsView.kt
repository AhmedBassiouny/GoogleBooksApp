package com.bassiouny.googlebooks.ui.bookdetails

import com.bassiouny.googlebooks.base.BaseView

interface BookDetailsView : BaseView {
    fun showDescription(description: String)
}