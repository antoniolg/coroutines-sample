package com.antonioleiva.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// NOTE: These extensions are written for academic purposes. Not meant to be used on a real project.

fun coroutine(context: CoroutineContext, block: suspend CoroutineScope.() -> Unit) =
    GlobalScope.launch(context, block = block)

suspend fun <T> suspended(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Default, block)