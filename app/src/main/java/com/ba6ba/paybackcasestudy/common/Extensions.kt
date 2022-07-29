package com.ba6ba.paybackcasestudy.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.ba6ba.paybackcasestudy.BR
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

const val EMPTY_STRING = ""

fun <T : ViewDataBinding> Fragment.dataBinding(factory: (View) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: factory(requireView()).also {
                // if binding is accessed after Lifecycle is DESTROYED, create new instance, but don't cache it
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    viewLifecycleOwner.lifecycle.addObserver(this)
                    binding = it
                    it.lifecycleOwner = viewLifecycleOwner
                }
            }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }
    }

fun Int?.default(other: Int? = null): Int =
    this ?: other ?: 0

fun String?.emptyIfNull() = this ?: EMPTY_STRING

val String?.default
    get() = emptyIfNull()

fun String?.default(other: String? = null) = this ?: other.default

val View.inflater
    get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Context.browseUrl(url: String?) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}