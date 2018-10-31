package repository


import api.TodoClientApi
import entity.Todo

class TodoCacheProxyRepository(val cacheRepository: Repository<Todo>)
	: Repository<Todo> {
	private val todoClientApi = TodoClientApi()

	override suspend fun create(todo: Todo): Todo {
		return try {
			todoClientApi.create(todo)
		} catch (e: Exception) {
			cacheRepository.create(todo)
		}
	}

	override suspend fun readAll(): List<Todo> {
		return try {
			val readedValues = todoClientApi.readAll()
			cacheRepository.readAll().forEach {
				cacheRepository.delete(it)
			}
			readedValues.forEach {
				cacheRepository.create(it)
			}
			readedValues
		} catch (e: Exception) {
			cacheRepository.readAll()
		}
	}

	override suspend fun read(id: String): Todo? {
		return try {
			val read = todoClientApi.read(id)
			cacheRepository.update(read)
			read
		} catch (e: Exception) {
			cacheRepository.read(id)
		}
	}

	override suspend fun update(todo: Todo): Todo? {
		return try {
			todoClientApi.update(todo)
			cacheRepository.update(todo)
		} catch (e: Exception) {
			cacheRepository.update(todo)
		}
	}

	override suspend fun delete(todo: Todo): Todo? {
		return try {
			todoClientApi.delete(todo)
			cacheRepository.delete(todo)
		} catch (e: Exception) {
			cacheRepository.delete(todo)
		}
	}

}
