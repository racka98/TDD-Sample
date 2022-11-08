package com.example.tdd.todo

class TodoPresenter(todoRepository: TodoRepository) {

    private val _notes = mutableListOf<TodoNote>()

    val notes: List<TodoNote> = _notes

    init {
        _notes.addAll(todoRepository.getNotes().sortedWith(NoteComparator().reversed()))
    }

    fun addNote() {
        _notes.add(0, TodoNote("", System.currentTimeMillis()))
    }

    private class NoteComparator : Comparator<TodoNote> {
        override fun compare(o1: TodoNote, o2: TodoNote): Int {
            return o1.createdTimeStamp.compareTo(o2.createdTimeStamp)
        }
    }
}