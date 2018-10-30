package server

import api.TodoClientApi
import entity.Status
import entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
	val todoClientApi = TodoClientApi()
	CoroutineScope(Dispatchers.Default).launch {
		println("added" + Todo.toJson(todoClientApi.create(Todo("12", "123213", Status.DONE))))
		println("updated" + Todo.toJson(todoClientApi.update(Todo("1", "it work2s", Status.IN_QUEUE))))
		println("first" + Todo.toJson(todoClientApi.read("1")))
		println("all" + Todo.listToJson(todoClientApi.readAll()))
		println("delete " + todoClientApi.delete(Todo("12", "it works", Status.IN_QUEUE)))
		println("all" + Todo.listToJson(todoClientApi.readAll()))
	}
	Thread.sleep(10000)
}