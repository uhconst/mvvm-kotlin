package com.constancio

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.constancio.data.local.db.AppDatabase
import com.constancio.data.local.db.code.CodeDao
import com.constancio.data.local.db.code.PathEntity
import com.constancio.data.local.db.code.ResponseCodeEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

/**
 * CodeDaoTest is a KoinTest with AndroidJUnit4 runner
 *
 * KoinTest help inject Koin components from actual runtime
 */
@RunWith(AndroidJUnit4::class)
class CodeDaoTest : KoinTest {

    /**
     * Inject AppDatabase from Koin
     */
    private val database: AppDatabase by inject()

    /**
     * Inject CodeDao from Koin
     */
    private val codeDao: CodeDao by inject()

    /**
     * Override default Koin configuration to use Room in-memory database
     */
    @Before
    fun before() {
        loadKoinModules(roomTestModule)
    }

    /**
     * Override default Koin configuration to close DB.
     */
    @After
    fun after() {
        database.close()
    }

    /**
     * Test inserting next path twice.
     * Also test if `getNextPath` returns the last path inserted.
     */
    @Test
    fun testInsertNextPath() {
        //Prepare
        val entity1 = getPathEntityMock(1)
        val entity2 = getPathEntityMock(2)

        //Action
        codeDao.insertNextPath(entity1)
        codeDao.insertNextPath(entity2)

        val requestedEntities = codeDao.getNextPath().blockingGet()

        //Test
        assertEquals(entity2, requestedEntities)
    }

    /**
     * Test inserting response code twice.
     * Also test if `getTimesFetchedQuantity` returns the right value.
     */
    @Test
    fun testInsertResponseCode() {
        //Prepare
        val entity1 = getResponseEntityMock(1)
        val entity2 = getResponseEntityMock(2)

        //Action
        codeDao.insertResponseCode(entity1)
        codeDao.insertResponseCode(entity2)

        val requestedEntities = codeDao.getTimesFetchedQuantity().blockingGet()

        //Test
        assertEquals(requestedEntities, 2)
    }

    private fun getPathEntityMock(id: Long = 1): PathEntity =
        PathEntity(
            id,
            "yyyyy-yyyyy"
        )

    private fun getResponseEntityMock(id: Long = 1): ResponseCodeEntity =
        ResponseCodeEntity(
            id,
            "xxxx-xxxx"
        )
}