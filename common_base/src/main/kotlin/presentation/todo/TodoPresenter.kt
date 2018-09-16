package presentation.todo

import entity.Status
import entity.Todo
import kotlinx.coroutines.experimental.launch
import presentation.base.BasePresenter
import kotlin.coroutines.experimental.CoroutineContext

class TodoPresenter(
		private val uiContext: CoroutineContext,
		private val todoInteractor: TodoInteractor) : BasePresenter<TodoView>() {

	override fun onViewAttached() {
		super.onViewAttached()
		refresh()
	}

	fun refresh() {
		launch(uiContext) {
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
