package com.sergiotorres.grazer.data.local

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiotorres.grazer.data.network.AuthNetworkDataSource
import com.sergiotorres.grazer.data.network.GrazerRetrofitService
import com.sergiotorres.grazer.data.network.dto.Auth
import com.sergiotorres.grazer.data.network.dto.AuthData
import com.sergiotorres.grazer.data.network.dto.AuthRequest
import com.sergiotorres.grazer.data.network.dto.AuthResponse
import com.sergiotorres.grazer.util.TestSingle
import io.reactivex.Single
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthLocalDataSourceTest {

    private val sharedPreferences: SharedPreferences = mock()

    private val underTest = AuthLocalDataSource(
        sharedPreferences = sharedPreferences
    )

    @Test
    fun `get gets from shared prefs`() {
        whenever(sharedPreferences.getString("authToken", null))
            .thenReturn("my_token")

        val actual = underTest.getAuthToken()

        assertEquals("my_token", actual)
    }

    @Test
    fun `get sets from shared prefs`() {
        val mockEditor: SharedPreferences.Editor = mock()
        whenever(sharedPreferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.putString("authToken", "my_token")).thenReturn(mockEditor)

        underTest.setAuthToken("my_token")

        verify(mockEditor).putString("authToken", "my_token")
    }
}