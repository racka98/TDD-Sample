package com.example.tdd

import com.example.tdd.todo.TodoNote
import com.example.tdd.todo.TodoPresenter
import com.example.tdd.todo.TodoRepository
import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class TodoPresenterTest {

    private val uniqueTimeStamps = generateSequence { Random.nextLong(0, 100000) }
        .take(10)
        .toList()

    private val fakeRepository = object : TodoRepository {
        override fun getNotes(): List<TodoNote> = (1..10).map {
            TodoNote(
                body = "Body $it",
                createdTimeStamp = uniqueTimeStamps[it - 1]
            )
        }
    }

    private val testSubjects = TodoPresenter(fakeRepository)

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
        val originalCont = testSubjects.notes.size

        testSubjects.addNote()

        val newCount = testSubjects.notes.size
        val firstNote = testSubjects.notes.first()
        assertEquals("Expected Note to be added", originalCont + 1, newCount)
        assertEquals("Expected Head of note to be empty", firstNote.body, "")
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