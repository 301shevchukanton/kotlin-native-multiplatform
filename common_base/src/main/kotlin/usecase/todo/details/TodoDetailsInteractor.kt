package presentation.todo.details

import entity.Todo

interface TodoDetailsInteractor {
	suspend fun read(todoId:String): Todo?
	suspend fun update(todo: Todo) : Todo?
	suspend fun delete(todo: Todo) : Boolean
}