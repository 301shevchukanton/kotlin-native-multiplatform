package repository


import entity.Todo
import kotlin.collections.LinkedHashMap

class InMemoryTodoRepository(initialUsers: Map<String, Todo>) : Repository<Todo> {
    private val todos = LinkedHashMap<String, Todo>()

    init {
        this.todos.putAll(initialUsers)
    }

    override fun createOne(todo: Todo): Todo {
        todos.put(todo.id, todo)
        return todo
    }

    override fun readAll(): List<Todo> = todos.values.toList()

    override fun readOne(id: String): Todo? = todos[id]

    override fun updateOne(todo: Todo): Todo? {
        return todos.replace(todo.id, todo)
    }

override fun deleteOne(todo: Todo): Todo? {
    return todos.remove(todo.id)
}

}
