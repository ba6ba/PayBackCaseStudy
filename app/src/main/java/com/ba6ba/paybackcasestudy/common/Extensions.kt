package com.ba6ba.paybackcasestudy.common

import android.app.AlertDialog
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
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.databinding.DialogConfirmationBinding
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

fun Long?.default(other: Long? = null): Long =
    this ?: other ?: 0L

fun String?.emptyIfNull() = this ?: EMPTY_STRING

val String?.default
    get() = emptyIfNull()

fun String?.default(other: String? = null) = this ?: other.default

val View.inflater
    get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Fragment.showConfirmationDialog(positiveButtonListener: () -> Unit) {
    val binding = DialogConfirmationBinding.inflate(LayoutInflater.from(context))
    val alertDialog = AlertDialog.Builder(context)
        .setView(binding.root)
        .create()
    alertDialog.window?.setBackgroundDrawableResource(R.drawable.rounded_corner_background)
    binding.dismissListener = object : DismissListener {
        override fun onDismiss(value: Any?) {
            value?.let {
                positiveButtonListener()
            }
            alertDialog.dismiss()
        }
    }
    alertDialog.show()
}

fun <E> List<E>.safeSubList(from: Int, to: Int): List<E> {
    return try {
        subList(if (from < 0) 0 else from, if (to >= count()) lastIndex else to)
    } catch (e: Exception) {
        this
    }
}

fun <T : Any> T.createList(size: Int): List<T> {
    val list = arrayListOf<T>()
    for (i in 0 .. size) {
        list.add(this)
    }
    return list
}