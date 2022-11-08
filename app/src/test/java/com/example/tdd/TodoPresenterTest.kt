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
        val noteText = "New Body"
        val noteIndex = 0
        testSubjects.updateNoteText(noteIndex, noteText)
        val updatedNote = testSubjects.notes[noteIndex]
        assertEquals("Expected Body to be same", noteText, updatedNote.body)
    }

    @Test
    fun `When I deleteNote, I expect the note to be remove from the list`() {
        val originalNotes = testSubjects.notes
        val originalCount = originalNotes.size
        val indexToDelete = 2
        val deletedNote = originalNotes[2]
        testSubjects.deleteNote(indexToDelete)
        val newNotes = testSubjects.notes
        val newCount = newNotes.size
        assertTrue("Expected to new count to be smaller than original", newCount < originalCount)
        assertFalse("Expected deletedNote to be absent", newNotes.contains(deletedNote))
    }
}