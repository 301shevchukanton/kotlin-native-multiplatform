package api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DefaultDispatcher


internal actual val ApplicationDispatcher: CoroutineDispatcher = DefaultDispatcher