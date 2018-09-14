package server

import api.TodoClientApi
import entity.Status
import entity.Todo
import kotlinx.coroutines.experimental.launch
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

fun main(args: Array<String>) {
    val todoClientApi = TodoClientApi()
    launch {
        println("added" + JSON.stringify(todoClientApi.add(Todo("12", "123213", Status.DONE))))
        println("updated" + JSON.stringify(todoClientApi.update(Todo("1", "it works", Status.IN_QUEUE))))
        println("first" + JSON.stringify(todoClientApi.get(1)))
        println("all" + JSON.stringify(Todo.serializer().list, todoClientApi.getAll()))
        println("delete " + todoClientApi.delete(Todo("12", "it works", Status.IN_QUEUE)))
        println("all" + JSON.stringify(Todo.serializer().list, todoClientApi.getAll()))
    }
    Thread.sleep(10000)
}