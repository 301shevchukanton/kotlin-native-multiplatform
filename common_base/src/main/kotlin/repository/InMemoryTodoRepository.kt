package repository


import entity.Todo

class InMemoryTodoRepository(initialTodos: Map<String, Todo>) : Repository<Todo> {
	private val todos = LinkedHashMap<String, Todo>()

	init {
		this.todos.putAll(initialTodos)
	}

	override suspend fun create(todo: Todo): Todo {
		val key = todo.id ?: todos.size.toString()
		todos[key] = todo.copy(id = key)
		return todo
	}

	override suspend fun readAll(): List<Todo> = todos.values.toList()

	override suspend fun read(id: String): Todo? = todos[id]

	override suspend fun update(todo: Todo): Todo? {
		todo.id?.let {
			return todos.put(it, todo)
		}
		return null
	}

	override suspend fun delete(todo: Todo): Todo? {
		return todos.remove(todo.id)
	}

}
