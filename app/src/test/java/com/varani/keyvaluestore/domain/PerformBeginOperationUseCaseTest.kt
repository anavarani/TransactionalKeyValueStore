package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PerformBeginOperationUseCaseTest {

    @Mock
    lateinit var mockCacheRepository: KeyValueStoreRepository

    private lateinit var sut: PerformBeginOperationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = PerformBeginOperationUseCase(mockCacheRepository)
    }

    @Test
    fun `Verify that beginNewTransaction was called`() {
        sut.invoke()
        verify(mockCacheRepository, times(1)).beginNewTransaction()
    }
}