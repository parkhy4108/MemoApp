package com.dev_young.note

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev_young.note.data.data_source.Dao
import com.dev_young.note.data.data_source.NoteDataBase
import com.dev_young.note.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NoteDatabaseDAOTest {

    private lateinit var noteDatabase: NoteDataBase
    private lateinit var noteDao: Dao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDatabase = Room.inMemoryDatabaseBuilder(context, NoteDataBase::class.java).build()
        noteDao = noteDatabase.dao()
    }

    @After
    fun closeDB(){
        noteDatabase.close()
    }


    @Test
    fun insertTest() = runTest {
        val note = Note(0,"0","0",0L,0)
        noteDao.upsertNote(note)
        val result = noteDao.getNotes().first()
        Assert.assertEquals(1, result.size)
    }

    @Test
    fun deleteTest() = runTest {
        val note = Note(0,"0","0",0L,0)
        noteDao.deleteNote(note = note)
        val result = noteDao.getNotes().first().size
        Assert.assertEquals(0, result)
    }

    @Test
    fun getNoteTest() = runTest {
        val note = Note(0,"0","0",0L,0)
        noteDao.upsertNote(note)
        val id = 0
        val result = noteDao.getNote(id)
        Assert.assertEquals(note, result)
    }

}
