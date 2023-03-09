package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.data.Transaction
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

class GetStackUseCaseTest {
    @Mock
    lateinit var mockCacheRepository: KeyValueStoreRepository

    private lateinit var sut: GetStackUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = GetStackUseCase(mockCacheRepository)
    }

    @Test
    fun `when stack is empty return empty string`() {
        `when`(mockCacheRepository.stack).thenReturn(LinkedList())
        val output = sut.invoke()
        assertEquals("", output)
    }

    @Test
    fun `when stack is not empty return string formatted`() {
        val stack = LinkedList<Transaction>()
        stack.add(Transaction(hashMapOf("test" to "value")))
        `when`(mockCacheRepository.stack).thenReturn(stack)
        val output = sut.invoke()
        assertEquals("test value", output)
    }
}