package presentation.todo.list

import entity.Todo

interface TodoListInteractor {
	suspend fun loadData(): List<Todo>
	fun addTodoItem(todo: Todo)

}