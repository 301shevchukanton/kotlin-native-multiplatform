package presentation.todo

import entity.Status
import entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.base.BasePresenter
import kotlin.coroutines.CoroutineContext

class TodoPresenter(
		private val uiContext: CoroutineContext,
		private val todoInteractor: TodoInteractor) : BasePresenter<TodoView>() {

	override fun onViewAttached() {
		super.onViewAttached()
		refresh()
	}

	fun refresh() {
		CoroutineScope(uiContext).launch {
			view?.showLoading(true)
			try {
				val todoList = todoInteractor.loadData()
				view?.showTodoList(todoList)
			} catch (e: Throwable) {
				view?.showError(e.message ?: "Can't load todo list")
			}
			view?.showLoading(false)
		}
	}

	fun onAddNewItemClicked(description: String, status: Status) {
		todoInteractor.addTodoItem(Todo(description = description, status = status))
	}
}
