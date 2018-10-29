package presentation.todo

import api.ApplicationDispatcher
import api.TodoClientApi
import entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TodoInteractorImpl : TodoInteractor {
	override fun addTodoItem(todo: Todo) {
		CoroutineScope(ApplicationDispatcher).launch {
			TodoClientApi().add(todo)
		}
	}

	override suspend fun loadData(): List<Todo> {
		return TodoClientApi().getAll()
	}

}