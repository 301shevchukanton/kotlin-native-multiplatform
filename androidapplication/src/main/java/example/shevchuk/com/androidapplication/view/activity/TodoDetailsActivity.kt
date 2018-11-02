package example.shevchuk.com.androidapplication.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import entity.Status
import example.shevchuk.com.androidapplication.R
import example.shevchuk.com.androidapplication.view.view_model.TodoDetailViewModel
import kotlinx.android.synthetic.main.activity_todo_details.*

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
	private var arrayAdapter: ArrayAdapter<String>? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_todo_details)
		subscribeOnViewModelChanges()
		parseInitialExtras()
		arrayAdapter = ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				Status.values().map { it.name })
		arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spStatus.adapter = arrayAdapter
		spStatus.onItemSelectedListener = onItemSelectedListener
		ivDelete.setOnClickListener {
			todoDetailsViewModel().delete()
		}
		etDescription.addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(s: Editable?) {
				todoDetailsViewModel().onDescriptionChanged(etDescription.text.toString())
			}

			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
		})
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
								spStatus.onItemSelectedListener = null
								spStatus.setSelection(
										arrayAdapter?.getPosition(todo.status.name)
												?: 0)
								spStatus.onItemSelectedListener = onItemSelectedListener
							}

							progress.visibility =
									if (it?.isInProgress == true) {
										View.VISIBLE
									} else {
										View.GONE
									}
						})

		todoDetailsViewModel()
				.errorLiveData
				.observe(this,
						android.arch.lifecycle.Observer {
							it?.handle {
								Toast.makeText(this,
										it.message,
										Toast.LENGTH_LONG)
										.show()
							}
						})
		todoDetailsViewModel()
				.closeLiveData
				.observe(this, Observer {
					if (it == true) {
						finish()
					}
				})
	}

	override fun onDestroy() {
		todoDetailsViewModel().todoLiveData.removeObservers(this)
		super.onDestroy()
	}

	private val onItemSelectedListener = object : OnItemSelectedListener {
		override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
			arrayAdapter?.getItem(position)?.let {
				todoDetailsViewModel().statusSelected(Status.valueOf(it))
			}
		}

		override fun onNothingSelected(parentView: AdapterView<*>) {
		}
	}
}