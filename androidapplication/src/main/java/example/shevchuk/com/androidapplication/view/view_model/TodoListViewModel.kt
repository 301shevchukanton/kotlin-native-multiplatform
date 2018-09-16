package example.shevchuk.com.androidapplication.view.view_model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import entity.Status
import entity.Todo
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import presentation.todo.TodoInteractorImpl
import presentation.todo.TodoPresenter
import presentation.todo.TodoView
import java.io.Serializable

/**
 * Created by Anton Shevchuk on 16.09.2018.
 */

class TodoListViewModel : ViewModel(), TodoView {

	class State(var todoItems: List<Todo> = emptyList(),
	            var isInProgress: Boolean = false) : Serializable

	val todoListLiveData = MutableLiveData<State>()
	val errorLiveData = MutableLiveData<ViewModelError<Throwable>>()
	private var todoPresenter: TodoPresenter

	init {
		this.todoListLiveData.value = State(emptyList(), true)
		this.todoPresenter = TodoPresenter(Dispatchers.Main, TodoInteractorImpl())
		this.todoPresenter.attach(this)
	}

	override fun onCleared() {
		this.todoPresenter.onViewDetached()
		super.onCleared()
	}

	fun loadList() {
		this.todoPresenter.refresh()
	}

	fun addTodoItem(description: String) {
		this.todoPresenter.onAddNewItemClicked(description, Status.IN_QUEUE)
	}

	override fun showTodoList(repoList: List<Todo>) {
		this.todoListLiveData.value = State(repoList, false)
	}

	override fun showLoading(loading: Boolean) {
		this.todoListLiveData.value = State(this.todoListLiveData.value?.todoItems ?: emptyList(),
				loading)
	}

	override fun showError(errorMessage: String) {
		this.errorLiveData.value = ViewModelError<Throwable>(Throwable(errorMessage))
	}

}