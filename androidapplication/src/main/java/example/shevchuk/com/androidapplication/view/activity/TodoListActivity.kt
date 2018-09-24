package example.shevchuk.com.androidapplication.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import example.shevchuk.com.androidapplication.R
import example.shevchuk.com.androidapplication.view.recycler_view.TodoAdapter
import example.shevchuk.com.androidapplication.view.view_model.TodoListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by Anton Shevchuk on 16.09.2018.
 */
class TodoListActivity : AppCompatActivity() {

	private lateinit var recyclerViewAdapter: TodoAdapter
	private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
	private var alertDialog: AlertDialog? = null

	private val viewModelClass = TodoListViewModel::class.java
	private fun todoListViewModel() = ViewModelProviders.of(this).get(viewModelClass)

	private val swipeToRefreshListener = SwipeRefreshLayout.OnRefreshListener {
		todoListViewModel().loadList()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initRecyclerView()
		srlRefresh.setOnRefreshListener(swipeToRefreshListener)
		srlRefresh.setColorSchemeResources(R.color.colorPrimary,
				android.R.color.holo_green_dark,
				android.R.color.holo_orange_dark,
				android.R.color.holo_blue_dark)

		floatingActionButton.setOnClickListener {
			showInputTodoDescriptionDialog()
		}
		subscribeOnViewModelChanges()
	}

	private fun initRecyclerView() {
		recyclerLayoutManager = LinearLayoutManager(this)
		recyclerViewAdapter = TodoAdapter(mutableListOf())
		rvTodoList.apply {
			setHasFixedSize(true)
			layoutManager = recyclerLayoutManager
			adapter = recyclerViewAdapter
		}
		rvTodoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)
				if (dy > 0 && floatingActionButton.getVisibility() == View.VISIBLE) {
					floatingActionButton.hide()
				} else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE) {
					floatingActionButton.show()
				}
			}
		})
	}

	private fun subscribeOnViewModelChanges() {
		todoListViewModel()
				.todoListLiveData
				.observe(this,
						android.arch.lifecycle.Observer {
							recyclerViewAdapter.setData(it?.todoItems ?: emptyList())
							srlRefresh.isRefreshing = it?.isInProgress == true
						})

		todoListViewModel()
				.errorLiveData
				.observe(this,
						android.arch.lifecycle.Observer {
							it?.handle {
								Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
							}
						})
	}

	override fun onStop() {
		alertDialog?.dismiss()
		super.onStop()
	}

	override fun onDestroy() {
		todoListViewModel().todoListLiveData.removeObservers(this)
		super.onDestroy()
	}

	private fun showInputTodoDescriptionDialog() {
		val builder = AlertDialog.Builder(this)
		builder.setTitle(getString(R.string.input_todo_dialog_title))

		val input = EditText(this)
		input.inputType = InputType.TYPE_CLASS_TEXT
		builder.setView(input)

		builder.setPositiveButton(getString(R.string.ok)) { _, _ ->
			todoListViewModel().addTodoItem(input.text.toString())
			GlobalScope.launch(Dispatchers.Default) {
				delay(2000)
				todoListViewModel().loadList()
			}
		}
		builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
			dialog.cancel()
		}
		alertDialog = builder.create()
		alertDialog?.show()
	}
}
