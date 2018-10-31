package presentation.todo.details

import entity.Todo
import presentation.base.BaseView

interface TodoDetailsView: BaseView {
    fun displayTodoDetails(todo: Todo)
    fun showLoading(loading: Boolean)
    fun close()
    fun onDescriptionChanged(description: String)
}