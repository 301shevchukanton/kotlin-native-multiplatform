package presentation.todo.list

import entity.Todo
import presentation.base.BaseView

interface TodoListView: BaseView {
    fun showTodoList(todoList: List<Todo>)
    fun showLoading(loading: Boolean)
    fun showError(errorMessage: String)
}