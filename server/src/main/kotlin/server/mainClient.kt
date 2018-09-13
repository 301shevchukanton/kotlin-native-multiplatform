package server

import api.TodoClientApi
import entity.Status
import entity.Todo
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

fun main(args: Array<String>) {
    TodoClientApi().add(Todo("12", "123213", Status.DONE))
    { println("added" + JSON.stringify(it)) }
    Thread.sleep(1000)
    TodoClientApi().update(Todo("1", "it works", Status.IN_QUEUE))
    { println("updated" + JSON.stringify(it)) }
    TodoClientApi().get(1) { println("first" + JSON.stringify(it)) }
    TodoClientApi().getAll { println("all" + JSON.stringify(Todo.serializer().list, it)) }
    Thread.sleep(1000)
    TodoClientApi().delete(Todo("12", "it works", Status.IN_QUEUE)){
         println("deleted" + JSON.stringify(it))
    }
    Thread.sleep(1000)
    TodoClientApi().getAll { println("all" + JSON.stringify(Todo.serializer().list, it)) }


    Thread.sleep(1000)
}