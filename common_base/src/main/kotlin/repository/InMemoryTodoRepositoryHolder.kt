package repository


import entity.Status
import entity.Todo

object InMemoryTodoRepositoryHolder{
	val inMemoryTodoRepository = InMemoryTodoRepository(mapOf("0" to Todo("0", "Clean the room", Status.IN_QUEUE),
			"1" to Todo("1", "Clean the table", Status.IN_PROGRESS)))

}
