package com.example.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    protected abstract fun executeFlow(parameter: P) : Flow<Result<R>>

    suspend operator fun invoke(parameter : P) = executeFlow(parameter)
        .catch { e -> emit(Result.Error(Exception(e))) }
        .flowOn(coroutineDispatcher)

}