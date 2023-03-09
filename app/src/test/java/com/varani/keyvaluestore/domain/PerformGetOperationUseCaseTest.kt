package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.domain.model.OperationError
import com.varani.keyvaluestore.domain.model.OperationResult
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class PerformGetOperationUseCaseTest {
    @Mock
    lateinit var mockCacheRepository: KeyValueStoreRepository

    private lateinit var sut: PerformGetOperationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = PerformGetOperationUseCase(mockCacheRepository)
    }

    @Test
    fun `verify that getValue was called`() {
        val key = "abc"
        val value = "123"
        `when`(mockCacheRepository.getValue(key)).thenReturn(value)
        val output = sut.invoke(key)
        assert(output is OperationResult.Success)
        verify(mockCacheRepository, times(1)).getValue(key)
    }

    @Test
    fun `when key is not found, return key is not set`() {
        val key = "abc"
        `when`(mockCacheRepository.getValue(key)).thenReturn("")
        val output = sut.invoke(key)
        assert(output is OperationResult.Error)
        assertEquals(OperationError.KeyNotSet, (output as OperationResult.Error).operationError)
    }
}