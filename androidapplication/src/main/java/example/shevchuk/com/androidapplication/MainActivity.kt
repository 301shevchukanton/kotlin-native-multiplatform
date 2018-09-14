package example.shevchuk.com.androidapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import entity.Status
import entity.Todo
import example.shevchuk.com.androidapplication.view.recycler_view.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import presentation.todo.TodoInteractorImpl
import presentation.todo.TodoPresenter
import presentation.todo.TodoView

class MainActivity : AppCompatActivity(), TodoView {

	private lateinit var recyclerViewAdapter: MyAdapter
	private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager

	private lateinit var todoPresenter: TodoPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		todoPresenter = TodoPresenter(Dispatchers.Main, TodoInteractorImpl())
		todoPresenter.attach(this)
		recyclerLayoutManager = LinearLayoutManager(this)
		recyclerViewAdapter = MyAdapter(mutableListOf())
		rvTodoList.apply {
			setHasFixedSize(true)
			layoutManager = recyclerLayoutManager
			adapter = recyclerViewAdapter
		}
		todoPresenter.refresh()
		floatingActionButton.setOnClickListener {
			todoPresenter.onAddNewItemClicked("12312", Status.IN_QUEUE)
		}
	}

	override fun onResume() {
		super.onResume()
		todoPresenter.refresh()
	}

	override fun showTodoList(repoList: List<Todo>) {
		recyclerViewAdapter.setData(repoList)
	}

	override fun showLoading(loading: Boolean) {
		progress.visibility = if (loading) View.VISIBLE else View.GONE
	}

	override fun showError(errorMessage: String) {
		Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
	}
}
