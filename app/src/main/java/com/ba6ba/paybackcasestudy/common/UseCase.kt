package com.ba6ba.paybackcasestudy.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface SuspendUseCase<R> {
    suspend operator fun invoke(): R = execute()

    suspend fun execute(): R
}

interface FlowUseCase<in P, R> {

    val dispatcher: CoroutineDispatcher

    operator fun invoke(parameters: P): Flow<R> = execute(parameters).flowOn(dispatcher)

    fun execute(parameters: P): Flow<R>
}