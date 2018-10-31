package example.shevchuk.com.androidapplication.view.view_model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import entity.Status
import entity.Todo
import kotlinx.coroutines.Dispatchers
import presentation.todo.details.TodoDetailsPresenter
import presentation.todo.details.TodoDetailsView
import java.io.Serializable

/**
 * Created by Anton Shevchuk on 16.09.2018.
 */

class TodoDetailViewModel : ViewModel(), TodoDetailsView {

	class State(var todo: Todo? = null,
	            var isInProgress: Boolean = false) : Serializable

	val todoLiveData = MutableLiveData<State>()
	val errorLiveData = MutableLiveData<ViewModelError<Throwable>>()
	val closeLiveData = MutableLiveData<Boolean>()
	private var todoDetailsPresenter: TodoDetailsPresenter

	init {
		this.todoLiveData.value = State(null, true)
		this.todoDetailsPresenter = TodoDetailsPresenter(Dispatchers.Main)
		this.todoDetailsPresenter.attach(this)
	}

	override fun onCleared() {
		this.todoDetailsPresenter.onViewDetached()
		super.onCleared()
	}

	override fun showLoading(loading: Boolean) {
		this.todoLiveData.value = State(this.todoLiveData.value?.todo,
				loading)
	}


	override fun close() {
		this.closeLiveData.value = true
	}

	override fun onDescriptionChanged(description: String) {
		this.todoDetailsPresenter.onDescriptionChanged(description)
	}

	fun onInitialDataParsed(todoId: String) {
		this.todoDetailsPresenter.onInitialDataParsed(todoId)
	}

	override fun displayTodoDetails(todo: Todo) {
		this.todoLiveData.value = TodoDetailViewModel.State(todo, false)
	}

	fun statusSelected(status: Status) {
		this.todoDetailsPresenter.onStatusChanged(status)
	}

	fun delete() {
		this.todoDetailsPresenter.onDeleteTodoClicked()
	}
}