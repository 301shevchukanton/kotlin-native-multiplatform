package presentation.todo

import api.TodoClientApi
import kotlinx.coroutines.experimental.launch
import presentation.base.BasePresenter
import kotlin.coroutines.experimental.CoroutineContext

class ReposPresenter(
        private val uiContext: CoroutineContext
) : BasePresenter<TodoView>() {

    override fun onViewAttached() {
        super.onViewAttached()
        refresh()
    }

    fun refresh() {
        launch(uiContext) {
            view?.showLoading(true)
            try {
                val todoList = TodoClientApi().getAll()
                view?.showTodoList(todoList)
            } catch (e: Throwable) {
                view?.showError(e.message ?: "Can't load todos")
            }
            view?.showLoading(false)
        }
    }
}
