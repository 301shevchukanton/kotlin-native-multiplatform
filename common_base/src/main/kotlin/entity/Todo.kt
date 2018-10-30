package entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

@Serializable
data class Todo(val id: String? = null,
                val description: String,
                val status: Status) {

	companion object {
		fun toJson(todo: Todo): String = JSON.stringify(serializer(), todo)

		fun fromJson(todoJson: String) = JSON.parse(serializer(), todoJson)

		fun listToJson(todoList: List<Todo>) =
				JSON.stringify(Todo.serializer().list, todoList)

		fun jsonArrayToList(todoArrayJson: String) =
				JSON.parse(Todo.serializer().list, todoArrayJson)
	}
}