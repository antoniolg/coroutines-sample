package com.antonioleiva.coroutines

import kotlinx.coroutines.*

// NOTE: These extensions are written for academic purposes. Not meant to be used on a real project.

fun coroutine(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(context = Dispatchers.Main, block = block)

suspend fun <T> suspended(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Default, block)