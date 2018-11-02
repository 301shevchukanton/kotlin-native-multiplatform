package api

import entity.Todo
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import repository.Repository

internal expect val ApplicationDispatcher: CoroutineDispatcher

class TodoClientApi : Repository<Todo> {
	companion object {
		const val PORT = 8088
		const val HOST_ADDRESS = "192.168.0.102"
		const val API_ROOT_LOCATION = "/api/v1/"
	}

	private val client = HttpClient()

	override suspend fun readAll(): List<Todo> {
		return withContext(ApplicationDispatcher) {
			val result: String = client.get {
				getHttpRequestBuilder(this,
						path = "${API_ROOT_LOCATION}getAll")
			}
			println(result)
			Todo.jsonArrayToList(result)
		}
	}

	override suspend fun read(itemIndex: String): Todo {
		return withContext(ApplicationDispatcher) {
			val result: String = client.get {
				getHttpRequestBuilder(this, path = "$API_ROOT_LOCATION$itemIndex")
			}
			println(result)
			Todo.fromJson(result)
		}
	}

	override suspend fun create(todo: Todo): Todo {
		return withContext(ApplicationDispatcher) {

			val result: String = client.post {
				getHttpRequestBuilder(this, todo = todo)
			}
			println(result)
			todo
		}
	}

	override suspend fun update(todo: Todo): Todo {
		return withContext(ApplicationDispatcher) {
			val result: String = client.put {
				getHttpRequestBuilder(this, todo = todo)
			}
			println(result)
			todo
		}
	}

	override suspend fun delete(todo: Todo): Todo {
		todo.id?.let {
			withContext(ApplicationDispatcher) {
				client.delete {
					getHttpRequestBuilder(this, path = "$API_ROOT_LOCATION${todo.id}")
				} as String
			}
			return todo
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