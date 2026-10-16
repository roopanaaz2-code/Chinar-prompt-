package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.BookRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Chinar Prompts", appName)
    }

    @Test
    fun `verify book data integrity`() {
        assertEquals(28, BookRepository.pages.size)
        assertEquals(119, BookRepository.allPrompts.size)
        assertEquals(12, BookRepository.categories.size)
        assertTrue(BookRepository.allPrompts.all { it.text.isNotBlank() })
    }
}
