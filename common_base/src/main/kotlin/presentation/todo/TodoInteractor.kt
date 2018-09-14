package presentation.todo

import entity.Todo

interface TodoInteractor {
	suspend fun loadData(): List<Todo>
	fun addTodoItem(todo: Todo)
}