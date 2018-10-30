package presentation.todo.list

import api.ApplicationDispatcher
import api.TodoClientApi
import entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TodoListInteractorImpl : TodoListInteractor {
	override fun addTodoItem(todo: Todo) {
		CoroutineScope(ApplicationDispatcher).launch {
			TodoClientApi().create(todo)
		}
	}

	override suspend fun loadData(): List<Todo> {
		return TodoClientApi().readAll()
	}

}