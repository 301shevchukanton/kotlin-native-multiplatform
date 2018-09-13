package presentation.todo

import entity.Todo
import presentation.base.BaseView

interface TodoView: BaseView {
    fun showTodoList(repoList: List<Todo>)
    fun showLoading(loading: Boolean)
    fun showError(errorMessage: String)
}