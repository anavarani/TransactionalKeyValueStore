package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PerformDeleteOperationUseCaseTest {
    @Mock
    lateinit var mockCacheRepository: KeyValueStoreRepository

    private lateinit var sut: PerformDeleteOperationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = PerformDeleteOperationUseCase(mockCacheRepository)
    }

    @Test
    fun `Verify that deleteKey was called`() {
        val key = "abc"
        sut.invoke(key)
        Mockito.verify(mockCacheRepository, Mockito.times(1)).deleteKey(key)
    }
}