package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class PerformCountOperationUseCaseTest {
    @Mock
    lateinit var mockCacheRepository: KeyValueStoreRepository

    private lateinit var sut: PerformCountOperationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = PerformCountOperationUseCase(mockCacheRepository)
    }

    @Test
    fun `Verify that countValues was called`() {
        val value = "123"
        sut.invoke(value)
        verify(mockCacheRepository, times(1)).countValues(value)
    }

    @Test
    fun `Return OperationResult Success with the count number`() {
        val mockValue = "123"
        val mockCount = 4
        `when`(mockCacheRepository.countValues(mockValue)).thenReturn(mockCount)
        val result = sut.invoke(mockValue)
        assertEquals(result.data, mockCount)
    }
}