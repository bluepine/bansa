package com.brianegan.listOfCounters

import android.view.View
import android.widget.LinearLayout
import com.brianegan.RxRedux.Action
import com.brianegan.RxRedux.Store
import trikita.anvil.Anvil
import trikita.anvil.DSL.*

fun counterView(model: CounterViewModel) {
    val (counter, increment, decrement, remove) = model

    linearLayout {
        size(FILL, WRAP)
        orientation(LinearLayout.HORIZONTAL)
        margin(0, dip(10))
        val parent = Anvil.currentView()

        textView {
            size(0, WRAP)
            weight(1f)
            id(R.id.counter_text)
            text("Counts: ${counter.toString()}")
        }

        button {
            id(R.id.counter_increment)
            size(WRAP, WRAP)
            padding(dip(10))
            text("+")
            onClick(increment)
        }

        button {
            id(R.id.counter_decrement)
            size(WRAP, WRAP)
            padding(dip(5))
            text("-")
            onClick(decrement)
        }
        button {
            size(WRAP, WRAP)
            padding(dip(5))
            text("remove")
            onClick(remove(parent))
        }
    }
}

fun buildMapCounterToCounterViewModel(store: Store<ApplicationState, Action>): (Counter) -> CounterViewModel {
    return { counter ->
        val (id) = counter

        CounterViewModel(
                counter = counter.value,
                increment = View.OnClickListener {
                    store.dispatch(INCREMENT(id))
                },
                decrement = View.OnClickListener {
                    store.dispatch(DECREMENT(id))
                },
                remove = { parent ->
                    View.OnClickListener { view ->
                        store.dispatch(REMOVE(id))
                    }
                })
    }
}

data class CounterViewModel(val counter: Int, val increment: View.OnClickListener, val decrement: View.OnClickListener, val remove: (View) -> View.OnClickListener)