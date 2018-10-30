package example.shevchuk.com.androidapplication.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import example.shevchuk.com.androidapplication.R
import example.shevchuk.com.androidapplication.view.view_model.TodoDetailViewModel
import kotlinx.android.synthetic.main.activity_todo_details.*


/**
 * Created by Anton Shevchuk on 16.09.2018.
 */
class TodoDetailsActivity : AppCompatActivity() {

	companion object {
		const val TODO_ID_KEY = "TODO_ID_KEY"
		fun createTodoDetailsActivityIntent(context: Context, todoId: String): Intent? {
			return Intent(context, TodoDetailsActivity::class.java)
					.putExtra(TODO_ID_KEY, todoId)
		}
	}

	private val viewModelClass = TodoDetailViewModel::class.java
	private fun todoDetailsViewModel() = ViewModelProviders.of(this).get(viewModelClass)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_todo_details)
		subscribeOnViewModelChanges()
		parseInitialExtras()
	}

	private fun parseInitialExtras() {
		intent.extras.get(TODO_ID_KEY)?.let {
			todoDetailsViewModel().onInitialDataParsed(it as String)
		}
	}

	private fun subscribeOnViewModelChanges() {
		todoDetailsViewModel()
				.todoLiveData
				.observe(this,
						android.arch.lifecycle.Observer {
							it?.todo?.let { todo ->
								etDescription.setText(todo.description)
							}
						})

		todoDetailsViewModel()
				.errorLiveData
				.observe(this,
						android.arch.lifecycle.Observer {
							it?.handle {
								Toast.makeText(this, it.message, Toast.LENGTH_LONG)
										.show()
							}
						})
	}

	override fun onDestroy() {
		todoDetailsViewModel().todoLiveData.removeObservers(this)
		super.onDestroy()
	}

}
