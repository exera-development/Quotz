package io.exera.quotz.tools

import io.exera.quotz.database.Quote

interface CardInterractionListener {
    fun onShare(quote: Quote)
    fun onSkip()
}