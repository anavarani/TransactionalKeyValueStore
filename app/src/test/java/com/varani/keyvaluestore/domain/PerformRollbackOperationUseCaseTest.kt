package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.data.Transaction
import com.varani.keyvaluestore.domain.model.OperationError
import com.varani.keyvaluestore.domain.model.OperationResult
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class PerformRollbackOperationUseCaseTest {
    @Mock
    lateinit var mockCacheRepository: KeyValueStoreRepository

    private lateinit var sut: PerformRollbackOperationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = PerformRollbackOperationUseCase(mockCacheRepository)
    }

    @Test
    fun `when stack is empty return no transaction error`() {
        Mockito.`when`(mockCacheRepository.stack).thenReturn(LinkedList())
        val output = sut.invoke()
        Assert.assertTrue(output is OperationResult.Error)
        Assert.assertEquals(
            OperationError.NoTransaction,
            (output as OperationResult.Error).operationError
        )
    }

    @Test
    fun `when stack has only 1 transaction no transaction error`() {
        val stack = LinkedList<Transaction>()
        stack.add(Transaction(hashMapOf("test" to "value")))
        Mockito.`when`(mockCacheRepository.stack).thenReturn(stack)
        val output = sut.invoke()
        Assert.assertTrue(output is OperationResult.Error)
        Assert.assertEquals(
            OperationError.NoTransaction,
            (output as OperationResult.Error).operationError
        )
    }

    @Test
    fun `when stack has more than 1 transaction perform commit`() {
        val stack = LinkedList<Transaction>()
        stack.addAll(
            listOf(
                Transaction(hashMapOf("k1" to "v1")),
                Transaction(hashMapOf("k2" to "v2"))
            )
        )
        Mockito.`when`(mockCacheRepository.stack).thenReturn(stack)
        sut.invoke()
        Mockito.verify(mockCacheRepository, Mockito.times(1)).rollbackLastTransaction()
    }

    @Test
    fun `when stack has more than 1 transaction return Success result`() {
        val stack = LinkedList<Transaction>()
        stack.addAll(
            listOf(
                Transaction(hashMapOf("k1" to "v1")),
                Transaction(hashMapOf("k2" to "v2"))
            )
        )
        Mockito.`when`(mockCacheRepository.stack).thenReturn(stack)
        val output = sut.invoke()
        Assert.assertTrue(output is OperationResult.Success)
    }
}