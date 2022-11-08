package com.example.tdd.todo

class TodoPresenter(todoRepository: TodoRepository) {
    val notes: List<TodoNote> = todoRepository.getNotes()
        .sortedWith(NoteComparator().reversed())

    private class NoteComparator : Comparator<TodoNote> {
        override fun compare(o1: TodoNote, o2: TodoNote): Int {
            return o1.createdTimeStamp.compareTo(o2.createdTimeStamp)
        }
    }
}