package api

import entity.Todo
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.URLProtocol
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.launch
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

internal expect val ApplicationDispatcher: CoroutineDispatcher

class TodoClientApi {
    private val client = HttpClient()
    fun getAll(callback: (List<Todo>) -> Unit) {
        launch(ApplicationDispatcher) {
            val result: String = client.get {
                getHttpRequestBuilder(this, path = "/getAll")
            }
            println(result)
            callback(JSON.parse(Todo.serializer().list, result))
        }
    }

    fun get(itemIndex: Int, callback: (Todo) -> Unit) {
        launch(ApplicationDispatcher) {
            val result: String = client.get {
                getHttpRequestBuilder(this, path = "/$itemIndex")
            }
            println(result)
            callback(JSON.parse(result))
        }
    }

    fun add(todo: Todo, callback: (Todo) -> Unit) {
        launch(ApplicationDispatcher) {
            val result: String = client.post {
                getHttpRequestBuilder(this, todo = todo)
            }
            println(result)
            callback(todo)
        }
    }

    fun update(todo: Todo, callback: (Todo) -> Unit) {
        launch(ApplicationDispatcher) {
            val result: String = client.put {
                getHttpRequestBuilder(this, todo = todo)
            }
            println(result)
            callback(todo)
        }
    }

    fun delete(todo: Todo, callback: (Boolean) -> Unit) {
       delete(todo.id, callback)
    }

    fun delete(id: String, callback: (Boolean) -> Unit) {
        launch(ApplicationDispatcher) {
            val result: String = client.delete {
                getHttpRequestBuilder(this, path = "/$id")
            }
            println(result)
            callback(true)
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