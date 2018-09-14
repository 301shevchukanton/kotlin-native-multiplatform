package presentation.todo

import api.ApplicationDispatcher
import api.TodoClientApi
import entity.Todo
import kotlinx.coroutines.experimental.launch

class TodoInteractorImpl : TodoInteractor {
	override fun addTodoItem(todo: Todo) {
		launch(ApplicationDispatcher) {
			TodoClientApi().add(todo)
		}
	}

	override suspend fun loadData(): List<Todo> {
		return TodoClientApi().getAll()
	}

}