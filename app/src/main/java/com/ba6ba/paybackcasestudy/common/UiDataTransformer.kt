package com.ba6ba.paybackcasestudy.common

interface UiDataTransformer<From, To> {
    fun transform(from: From): To
}