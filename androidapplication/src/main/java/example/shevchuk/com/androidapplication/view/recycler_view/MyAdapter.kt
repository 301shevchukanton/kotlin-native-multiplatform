package example.shevchuk.com.androidapplication.view.recycler_view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TwoLineListItem
import entity.Todo

class MyAdapter(private val myDataset: MutableList<Todo>) :
		RecyclerView.Adapter<MyAdapter.TodoViewHolder>() {

	class TodoViewHolder(val twoLineListItem: TwoLineListItem) : RecyclerView.ViewHolder(twoLineListItem)


	override fun onCreateViewHolder(parent: ViewGroup,
	                                viewType: Int): MyAdapter.TodoViewHolder {
		val view = LayoutInflater.from(parent.context)
				.inflate(android.R.layout.simple_list_item_2, parent, false) as TwoLineListItem
		return TodoViewHolder(view)
	}

	override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
		holder.twoLineListItem.text1.text = myDataset[position].description
		holder.twoLineListItem.text2.text = myDataset[position].status.toString()
	}

	override fun getItemCount() = myDataset.size

	fun setData(list: List<Todo>) {
		this.myDataset.clear()
		this.myDataset.addAll(list)
		notifyDataSetChanged()
	}
}