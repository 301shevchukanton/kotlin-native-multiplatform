package server

import api.TodoClientApi
import api.TodoClientApi.Companion.API_ROOT_LOCATION
import com.github.salomonbrys.kodein.factory
import dependency_injection.Injection
import entity.Status
import entity.Todo
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import repository.Repository

fun main(args: Array<String>) {

	val repository =
			Injection()
					.kodein
					.factory<Map<String, Todo>, Repository<Todo>>(Injection.MEMORY_REPOSITORY_TAG)
					.invoke(mapOf("0" to Todo("0", "Clean the room", Status.IN_QUEUE),
							"1" to Todo("1", "Clean the table", Status.IN_PROGRESS)))


	embeddedServer(Netty, TodoClientApi.PORT) {

		routing {
			get("${API_ROOT_LOCATION}getAll") {
				call.respondText(
						Todo.listToJson(repository.readAll()),
						ContentType.Text.Html)
			}
			get("$API_ROOT_LOCATION{key}") {
				val item = repository.read(call.parameters["key"] ?: "")
				if (item == null)
					call.respond(HttpStatusCode.NotFound)
				else
					call.respond(Todo.toJson(item))
			}
			post(API_ROOT_LOCATION) {
				try {
					val str = call.receiveText()
					val item = Todo.fromJson(str)
					repository.create(item)
					call.respond(HttpStatusCode.Created)
				} catch (e: Throwable) {
					call.respond(HttpStatusCode.BadRequest)
				}
			}
			put(API_ROOT_LOCATION) {
				val todo = Todo.fromJson(call.receiveText())
				try {
					repository.update(todo)
					call.respond(HttpStatusCode.OK)
				} catch (e: Exception) {
					call.respond(HttpStatusCode.BadRequest)
				}
			}
			delete("$API_ROOT_LOCATION{key}") {
				val todoId = call.parameters["key"]
				todoId?.let { realTodoId ->
					repository.read(realTodoId)?.let { readTodo ->
						repository.delete(readTodo)
					}
				}
				call.respond(HttpStatusCode.OK)
			}
		}
	}.start(wait = true)
}