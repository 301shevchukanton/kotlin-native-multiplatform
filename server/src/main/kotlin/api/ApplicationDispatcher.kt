package api

import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.DefaultDispatcher

internal actual val ApplicationDispatcher: CoroutineDispatcher = DefaultDispatcher