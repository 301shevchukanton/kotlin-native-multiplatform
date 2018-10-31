package presentation.todo.details

import entity.Todo
import repository.Repository

class TodoDetailsInteractorImpl(val repository: Repository<Todo>) : TodoDetailsInteractor {

	override suspend fun read(todoId: String): Todo? {
		return repository.read(todoId)
	}

	override suspend fun update(todo: Todo): Todo? {
		return repository.update(todo)
	}

	override suspend fun delete(todo: Todo): Boolean {
		return repository.delete(todo) != null
	}

}