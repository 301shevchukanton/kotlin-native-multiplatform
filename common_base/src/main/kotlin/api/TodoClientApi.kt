package api

import entity.Todo
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.URLProtocol
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

internal expect val ApplicationDispatcher: CoroutineDispatcher

class TodoClientApi {
    private val client = HttpClient()
    suspend fun getAll(): List<Todo> {
        return withContext(ApplicationDispatcher) {
            val result: String = client.get {
                getHttpRequestBuilder(this, path = "/getAll")
            }
            println(result)
            JSON.parse(Todo.serializer().list, result)
        }
    }

    suspend fun get(itemIndex: Int): Todo {
        return withContext(ApplicationDispatcher) {
            val result: String = client.get {
                getHttpRequestBuilder(this, path = "/$itemIndex")
            }
            println(result)
            JSON.parse<Todo>(result)
        }
    }

    suspend fun add(todo: Todo): Todo {
        return withContext(ApplicationDispatcher) {

            val result: String = client.post {
                getHttpRequestBuilder(this, todo = todo)
            }
            println(result)
            todo
        }
    }

    suspend fun update(todo: Todo): Todo {
        return withContext(ApplicationDispatcher) {
            val result: String = client.put {
                getHttpRequestBuilder(this, todo = todo)
            }
            println(result)
            todo
        }
    }

    suspend fun delete(todo: Todo): Boolean {
        return delete(todo.id)
    }

    suspend fun delete(id: String): Boolean {
        return withContext(ApplicationDispatcher) {
            val result: String = client.delete {
                getHttpRequestBuilder(this, path = "/$id")
            }
            println(result)
            true
        }
    }

    private fun getHttpRequestBuilder(httpRequestBuilder: HttpRequestBuilder,
                                      path: String = "/",
                                      todo: Todo? = null) {
        httpRequestBuilder.url {
            protocol = URLProtocol.HTTP
            port = 8080
            host = "localhost"
            encodedPath = path
        }
        todo?.apply {
            httpRequestBuilder.body = JSON.stringify(todo)
        }
    }
}