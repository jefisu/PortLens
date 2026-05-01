package com.jefisu.portlens.core.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.jefisu.portlens.core.database.entity.CompetenceRecord
import com.jefisu.portlens.core.database.entity.DashboardSnapshotEntity
import com.jefisu.portlens.core.database.entity.DashboardTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DashboardSnapshotDao {
    @Query("SELECT COUNT(*) FROM dashboard_snapshots")
    suspend fun getSnapshotCount(): Int

    @Upsert
    suspend fun upsertSnapshots(snapshots: List<DashboardSnapshotEntity>)

    @Upsert
    suspend fun upsertTransactions(transactions: List<DashboardTransactionEntity>)

    @Query("DELETE FROM latest_transactions")
    suspend fun deleteAllTransactions()

    @Query(
        """
        SELECT *
        FROM dashboard_snapshots
        WHERE competenceYear = :year
          AND competenceMonth = :month
        """,
    )
    fun observeSnapshot(year: Int, month: Int): Flow<DashboardSnapshotEntity?>

    @Query(
        """
        SELECT *
        FROM latest_transactions
        WHERE competenceYear = :year
          AND competenceMonth = :month
        ORDER BY orderIndex ASC
        """,
    )
    fun observeTransactions(year: Int, month: Int): Flow<List<DashboardTransactionEntity>>

    @Query(
        """
        SELECT competenceYear, competenceMonth
        FROM dashboard_snapshots
        ORDER BY competenceYear ASC, competenceMonth ASC
        """,
    )
    fun observeAvailableCompetences(): Flow<List<CompetenceRecord>>
}
