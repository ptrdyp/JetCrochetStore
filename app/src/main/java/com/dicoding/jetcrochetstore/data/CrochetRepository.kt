package com.dicoding.jetcrochetstore.data

import com.dicoding.jetcrochetstore.model.CrochetData
import com.dicoding.jetcrochetstore.model.OrderCrochet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class CrochetRepository {
    private val orderCrochets = mutableListOf<OrderCrochet>()

    init {
        if (orderCrochets.isEmpty()) {
            CrochetData.dummyCrochets.forEach{
                orderCrochets.add(OrderCrochet(it, 0))
            }
        }
    }

    fun getAllCrochets(): Flow<List<OrderCrochet>> {
        return flowOf(orderCrochets)
    }

    fun getOrderCrochetById(crochetId: Long): OrderCrochet {
        return orderCrochets.first{
            it.crochet.id == crochetId
        }
    }

    fun updateOrderCrochet(crochetId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderCrochets.indexOfFirst { it.crochet.id == crochetId }
        val result = if (index >= 0) {
            val orderCrochet = orderCrochets[index]
            orderCrochets[index] = orderCrochet.copy(
                crochet = orderCrochet.crochet, count = newCountValue
            )
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderCrochets(): Flow<List<OrderCrochet>> {
        return getAllCrochets()
            .map { orderCrochets ->
                orderCrochets.filter { orderCrochet ->
                    orderCrochet.count != 0
                }
            }
    }

    companion object {
        private var INSTANCE: CrochetRepository? = null

        fun getInstance(): CrochetRepository =
            INSTANCE ?: synchronized(this) {
                CrochetRepository().apply {
                    INSTANCE = this
                }
            }
    }
}