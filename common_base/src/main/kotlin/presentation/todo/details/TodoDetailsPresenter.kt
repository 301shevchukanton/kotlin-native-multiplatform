package presentation.todo.details

import entity.Status
import entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.base.BasePresenter
import repository.InMemoryTodoRepositoryHolder
import repository.TodoCacheProxyRepository
import kotlin.coroutines.CoroutineContext

class TodoDetailsPresenter(
		private val uiContext: CoroutineContext,
		private val todoDetailsInteractor: TodoDetailsInteractor = TodoDetailsInteractorImpl(
				TodoCacheProxyRepository(InMemoryTodoRepositoryHolder.inMemoryTodoRepository))
) : BasePresenter<TodoDetailsView>() {
	private var todo: Todo? = null

	fun onInitialDataParsed(todoId: String) {
		CoroutineScope(uiContext).launch {
			view?.showLoading(true)
			view?.displayTodoDetails(
					withContext(uiContext) {
						todo = todoDetailsInteractor.read(todoId)
						todo!!
					})
			view?.showLoading(false)
		}
	}

	fun onStatusChanged(status: Status) {
		CoroutineScope(uiContext).launch {
			todo?.let {
				todo = it.copy(status = status)
				todoDetailsInteractor.update(todo!!)
				view?.displayTodoDetails(todo!!)
			}
		}
	}

	fun onDescriptionChanged(description: String) {
		CoroutineScope(uiContext).launch {
			todo?.let {
				todo = it.copy(description = description)
				todoDetailsInteractor.update(todo!!)
			}
		}
	}

	fun onDeleteTodoClicked() {
		CoroutineScope(uiContext).launch {
			todo?.let {
				todoDetailsInteractor.delete(it)
			}
			view?.close()
		}
	}
}
