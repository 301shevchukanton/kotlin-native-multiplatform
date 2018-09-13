package repository

import entity.Todo


interface Repository<T> {
    fun createOne(t: T): T
    fun readAll(): List<T>
    fun readOne(id: String): Todo?
    fun updateOne(t: T): Todo?
    fun deleteOne(t: T): Todo?
}