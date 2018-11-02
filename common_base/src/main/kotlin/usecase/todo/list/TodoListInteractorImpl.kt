package presentation.todo.list

import api.ApplicationDispatcher
import entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import repository.Repository

class TodoListInteractorImpl(
		private val todoListRepository: Repository<Todo>)
	: TodoListInteractor {
	override fun addTodoItem(todo: Todo) {
		CoroutineScope(ApplicationDispatcher).launch {
			todoListRepository.create(todo)
		}
	}

	override suspend fun loadTodoList(): List<Todo> {
		return todoListRepository.readAll()
	}

}