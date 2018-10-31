package presentation.todo.details

import api.TodoClientApi
import entity.Status
import entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.base.BasePresenter
import kotlin.coroutines.CoroutineContext

class TodoDetailsPresenter(
		private val uiContext: CoroutineContext) : BasePresenter<TodoDetailsView>() {

	private val todoClientApi = TodoClientApi()
	private var todo: Todo? = null

	fun onInitialDataParsed(todoId: String) {
		CoroutineScope(uiContext).launch {
			view?.showLoading(true)
			view?.displayTodoDetails(
					withContext(uiContext) {
						todo = todoClientApi.read(todoId)
						todo!!
					})
			view?.showLoading(false)
		}
	}

	fun onStatusChanged(status: Status) {
		CoroutineScope(uiContext).launch {
			todo?.let {
				todo = it.copy(status = status)
				todoClientApi.update(todo!!)
				view?.displayTodoDetails(todo!!)
			}
		}
	}

	fun delete() {
		CoroutineScope(uiContext).launch {
			todo?.let {
				todoClientApi.delete(it)
			}
			view?.close()
		}
	}

	fun onDescriptionChanged(description: String) {
		CoroutineScope(uiContext).launch {
			todo?.let {
				todo = it.copy(description = description)
				todoClientApi.update(todo!!)
			}
		}
	}
}
