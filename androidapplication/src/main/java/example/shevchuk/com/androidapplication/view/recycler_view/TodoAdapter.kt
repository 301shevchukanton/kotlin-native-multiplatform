package example.shevchuk.com.androidapplication.view.recycler_view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TwoLineListItem
import entity.Todo

class TodoAdapter(private val myDataSet: MutableList<Todo>, private val todoListClickListener: OnItemClickListener) :
		RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

	class TodoViewHolder(val twoLineListItem: TwoLineListItem) : RecyclerView.ViewHolder(twoLineListItem)


	override fun onCreateViewHolder(parent: ViewGroup,
	                                viewType: Int): TodoAdapter.TodoViewHolder {
		val view = LayoutInflater.from(parent.context)
				.inflate(android.R.layout.simple_list_item_2, parent, false) as TwoLineListItem
		return TodoViewHolder(view)
	}

	override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
		holder.twoLineListItem.text1.text = myDataSet[position].description
		holder.twoLineListItem.text2.text = myDataSet[position].status.toString()
		holder.twoLineListItem.setOnClickListener {
			todoListClickListener.onItemClick(myDataSet[position])
		}
	}

	override fun getItemCount() = myDataSet.size

	fun setData(list: List<Todo>) {
		this.myDataSet.clear()
		this.myDataSet.addAll(list)
		notifyDataSetChanged()
	}
}