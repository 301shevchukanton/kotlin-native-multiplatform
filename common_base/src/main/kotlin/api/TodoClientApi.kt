package api

import entity.Todo
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal expect val ApplicationDispatcher: CoroutineDispatcher

class TodoClientApi {
	companion object {
		val PORT = 8080
		val HOST_ADDRESS = "127.0.0.1"
		val API_ROOT_LOCATION = "/api/v1/"
	}

	private val client = HttpClient()

	suspend fun getAll(): List<Todo> {
		return withContext(ApplicationDispatcher) {
			val result: String = client.get {
				getHttpRequestBuilder(this, path = "${API_ROOT_LOCATION}getAll")
			}
			println(result)
			Todo.jsonArrayToList(result)
		}
	}

	suspend fun get(itemIndex: Int): Todo {
		return withContext(ApplicationDispatcher) {
			val result: String = client.get {
				getHttpRequestBuilder(this, path = "$API_ROOT_LOCATION$itemIndex")
			}
			println(result)
			Todo.fromJson(result)
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
		return todo.id?.let {
			return delete(todo.id)
		} ?: false
	}

	suspend fun delete(id: String): Boolean {
		return withContext(ApplicationDispatcher) {
			val result: String = client.delete {
				getHttpRequestBuilder(this, path = "$API_ROOT_LOCATION$id")
			}
			println(result)
			true
		}
	}

	private fun getHttpRequestBuilder(httpRequestBuilder: HttpRequestBuilder,
	                                  path: String = API_ROOT_LOCATION,
	                                  todo: Todo? = null) {
		httpRequestBuilder.url {
			protocol = URLProtocol.HTTP
			port = PORT
			host = HOST_ADDRESS
			encodedPath = path
		}
		todo?.apply {
			httpRequestBuilder.body = Todo.toJson(todo)
		}
	}
}