package presentation.todo.list

import entity.Todo

interface TodoListInteractor {
	suspend fun loadTodoList(): List<Todo>
	fun addTodoItem(todo: Todo)

}