package entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

@Serializable
data class Todo(val id: String,
                val description: String,
                val status: Status) {


    companion object {
        fun toJson(todo: Todo): String {
            return JSON.stringify(todo)
        }
    }
}