package server

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
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import repository.Repository

fun main(args: Array<String>) {

    val repository =
            Injection()
                    .kodein
                    .factory<Map<String, Todo>, Repository<Todo>>(Injection.MEMORY_REPOSITORY_TAG)
                    .invoke(mapOf("0" to Todo("0", "Clean the room", Status.IN_QUEUE),
                            "1" to Todo("1", "Clean the table", Status.IN_PROGRESS)))


    embeddedServer(Netty, 8080) {

        routing {
            get("/getAll") {
                println("get all request")
                call.respondText(
                        JSON.stringify(Todo.serializer().list,
                                repository
                                        .readAll()),
                        ContentType.Text.Html)
            }
            get("/{key}") {
                val item = repository.readOne(call.parameters["key"] ?: "")
                if (item == null)
                    call.respond(HttpStatusCode.NotFound)
                else
                    call.respond(Todo.toJson(item))
            }
            post("/") {
                try {
                    val str = call.receiveText()
                    val item = JSON.parse<Todo>(str)
                    repository.createOne(item)
                    call.respond(HttpStatusCode.Created)
                } catch (e: Throwable) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            put("/") {
                val todo = JSON.parse<Todo>(call.receiveText())
                repository.updateOne(todo)
            }
            delete("/{key}") {
                val todoId = call.parameters["key"]
                todoId?.let {realTodoId ->
                            repository.readOne(realTodoId)?.let {readTodo ->
                                repository.deleteOne(readTodo)
                            }

                }
                call.respond(HttpStatusCode.OK)
            }

        }
    }.start(wait = true)
}