package com.example.tdd

import com.example.tdd.todo.TodoPresenter
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class TodoPresenterTest {

    private val testSubjects = TodoPresenter()
    @Test
    fun `When I init, I expect notes in reverse chronological order`() {
        val notes = testSubjects.notes
        val isReverseChron = notes
            .windowed(2, 1)
            .all { (a, b) -> a.createdTimeStamp > b.createdTimeStamp }
        assertTrue(isReverseChron)
    }

    @Test
    fun `When I addNote, I expect a new note at the head of the list`() {
        fail()
    }

    @Test
    fun `When I updateNote, I expect the note to be updated with given text`() {
        fail()
    }

    @Test
    fun `When I deleteNote, I expect the note to be remove from the list`() {
        fail()
    }
}