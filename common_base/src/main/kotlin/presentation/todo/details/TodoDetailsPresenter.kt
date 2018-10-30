package presentation.todo.details

import api.TodoClientApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.base.BasePresenter
import kotlin.coroutines.CoroutineContext

class TodoDetailsPresenter(
		private val uiContext: CoroutineContext) : BasePresenter<TodoDetailsView>() {

	private val todoClientApi = TodoClientApi()

	fun onInitialDataParsed(todo: String) {
		CoroutineScope(uiContext).launch {
			view?.showLoading(true)
			view?.displayTodoDetails(withContext(uiContext) {
				todoClientApi.read(todo)
			})
			view?.showLoading(false)
		}
	}
}
