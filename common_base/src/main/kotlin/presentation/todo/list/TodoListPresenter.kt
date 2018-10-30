package presentation.todo.list

import entity.Status
import entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.base.BasePresenter
import kotlin.coroutines.CoroutineContext

class TodoListPresenter(
		private val uiContext: CoroutineContext,
		private val todoListInteractor: TodoListInteractor) : BasePresenter<TodoListView>() {

	override fun onViewAttached() {
		super.onViewAttached()
		refresh()
	}

	fun refresh() {
		CoroutineScope(uiContext).launch {
			view?.showLoading(true)
			try {
				val todoList = todoListInteractor.loadData()
				view?.showTodoList(todoList)
			} catch (e: Throwable) {
				view?.showError(e.message ?: "Can't load todo list")
			}
			view?.showLoading(false)
		}
	}

	fun onAddNewItemClicked(description: String, status: Status) {
		todoListInteractor.addTodoItem(Todo(description = description, status = status))
	}
}
