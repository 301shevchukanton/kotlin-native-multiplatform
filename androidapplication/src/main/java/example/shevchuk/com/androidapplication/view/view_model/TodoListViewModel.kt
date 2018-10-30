package example.shevchuk.com.androidapplication.view.view_model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import entity.Status
import entity.Todo
import kotlinx.coroutines.Dispatchers
import presentation.todo.list.TodoListInteractorImpl
import presentation.todo.list.TodoListPresenter
import presentation.todo.list.TodoListView
import java.io.Serializable

/**
 * Created by Anton Shevchuk on 16.09.2018.
 */

class TodoListViewModel : ViewModel(), TodoListView {

	class State(var todoItems: List<Todo> = emptyList(),
	            var isInProgress: Boolean = false) : Serializable

	val todoListLiveData = MutableLiveData<State>()
	val errorLiveData = MutableLiveData<ViewModelError<Throwable>>()
	private var todoListPresenter: TodoListPresenter

	init {
		this.todoListLiveData.value = State(emptyList(), true)
		this.todoListPresenter = TodoListPresenter(Dispatchers.Main, TodoListInteractorImpl())
		this.todoListPresenter.attach(this)
	}

	override fun onCleared() {
		this.todoListPresenter.onViewDetached()
		super.onCleared()
	}

	fun loadList() {
		this.todoListPresenter.refresh()
	}

	fun addTodoItem(description: String) {
		this.todoListPresenter.onAddNewItemClicked(description, Status.IN_QUEUE)
	}

	override fun showTodoList(todoList: List<Todo>) {
		this.todoListLiveData.value = State(todoList, false)
	}

	override fun showLoading(loading: Boolean) {
		this.todoListLiveData.value = State(this.todoListLiveData.value?.todoItems ?: emptyList(),
				loading)
	}

	override fun showError(errorMessage: String) {
		this.errorLiveData.value = ViewModelError<Throwable>(Throwable(errorMessage))
	}

}