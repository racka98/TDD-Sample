package com.example.tdd.todo

interface TodoRepository {
    fun getNotes(): List<TodoNote>
}