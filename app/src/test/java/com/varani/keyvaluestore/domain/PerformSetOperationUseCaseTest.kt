package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.domain.model.OperationError
import com.varani.keyvaluestore.domain.model.OperationResult
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class PerformSetOperationUseCaseTest {
    @Mock
    lateinit var mockCacheRepository: KeyValueStoreRepository

    private lateinit var sut: PerformSetOperationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = PerformSetOperationUseCase(mockCacheRepository)
    }

    @Test
    fun `verify that getValue was called`() {
        val key = "abc"
        val value = "123"
        sut.invoke(key, value)
        verify(mockCacheRepository, times(1)).setKeyValue(key, value)
    }

    @Test
    fun `when key is not found, return key is not set`() {
        val key = "abc"
        val value = "123"
        val output = sut.invoke(key, value)
        assert(output is OperationResult.Success)
    }

    @Test
    fun `when exception is thrown on setKeyValue, returns an error result`() {
        val key = "abc"
        val value = "123"
        `when`(
            mockCacheRepository.setKeyValue(
                key,
                value
            )
        ).thenThrow(NoSuchElementException::class.java)
        val output = sut.invoke(key, value)
        assert(output is OperationResult.Error)
        Assert.assertEquals(
            OperationError.ErrorDuringKeySet,
            (output as OperationResult.Error).operationError
        )
    }
}