package repository


import entity.Todo

class InMemoryTodoRepository(initialUsers: Map<String, Todo>) : Repository<Todo> {
	private val todos = LinkedHashMap<String, Todo>()

	init {
		this.todos.putAll(initialUsers)
	}

	override fun createOne(todo: Todo): Todo {
		val key = todo.id ?: todos.size.toString()
		todos.put(key, todo.copy(id = key))
		return todo
	}

	override fun readAll(): List<Todo> = todos.values.toList()

	override fun readOne(id: String): Todo? = todos[id]

	override fun updateOne(todo: Todo): Todo? {
		todo.id?.let {
			return todos.replace(it, todo)
		}
		return null
	}

	override fun deleteOne(todo: Todo): Todo? {
		return todos.remove(todo.id)
	}

}
