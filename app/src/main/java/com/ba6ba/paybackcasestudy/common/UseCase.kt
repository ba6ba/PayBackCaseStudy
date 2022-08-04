package com.ba6ba.paybackcasestudy.common

interface SuspendUseCase<R> {
    suspend operator fun invoke(): R = execute()

    suspend fun execute(): R
}

interface UseCase<in P, R> {

    operator fun invoke(parameters: P): R = execute(parameters)

    fun execute(parameters: P): R
}