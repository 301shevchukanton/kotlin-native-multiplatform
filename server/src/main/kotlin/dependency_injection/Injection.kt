package dependency_injection

import com.github.salomonbrys.kodein.*
import entity.Todo
import repository.InMemoryTodoRepository
import repository.Repository


class Injection {

    companion object {
        val MEMORY_REPOSITORY_TAG = "memory"
    }

    val kodein = Kodein {
        bind<Repository<Todo>>(MEMORY_REPOSITORY_TAG) with factory { todos: Map<String, Todo> ->
           InMemoryTodoRepository(todos)
        }
    }
}