package repository


interface Repository<T> {
	suspend fun create(t: T): T
	suspend fun readAll(): List<T>
	suspend fun read(id: String): T?
	suspend fun update(t: T): T?
	suspend fun delete(t: T): T?
}