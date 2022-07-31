package com.ba6ba.paybackcasestudy.common

import java.io.Serializable

@FunctionalInterface
interface DismissListener : Serializable {
    fun onDismiss(value: Any? = null)
}