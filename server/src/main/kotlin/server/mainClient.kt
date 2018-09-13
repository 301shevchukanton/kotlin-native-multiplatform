package server

import api.TodoClientApi
import entity.Status
import entity.Todo
import kotlinx.coroutines.experimental.launch
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

fun main(args: Array<String>) {
    launch {
        println("added" + JSON.stringify(TodoClientApi().add(Todo("12", "123213", Status.DONE))))
        println("updated" + JSON.stringify(TodoClientApi().update(Todo("1", "it works", Status.IN_QUEUE))))
        println("first" + JSON.stringify(TodoClientApi().get(1)))
        println("all" + JSON.stringify(Todo.serializer().list, TodoClientApi().getAll()))
        println("delete " + TodoClientApi().delete(Todo("12", "it works", Status.IN_QUEUE)))
        println("all" + JSON.stringify(Todo.serializer().list, TodoClientApi().getAll()))
    }
    Thread.sleep(10000)
}