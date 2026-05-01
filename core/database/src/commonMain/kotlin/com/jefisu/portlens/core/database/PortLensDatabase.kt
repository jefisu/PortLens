package com.jefisu.portlens.core.database

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import com.jefisu.portlens.core.database.dao.DashboardSnapshotDao
import com.jefisu.portlens.core.database.entity.DashboardSnapshotEntity
import com.jefisu.portlens.core.database.entity.DashboardTransactionEntity

@ConstructedBy(PortLensDatabaseConstructor::class)
@Database(
    entities = [DashboardSnapshotEntity::class, DashboardTransactionEntity::class],
    version = 2,
)
abstract class PortLensDatabase : RoomDatabase() {
    abstract fun dashboardSnapshotDao(): DashboardSnapshotDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PortLensDatabaseConstructor : RoomDatabaseConstructor<PortLensDatabase>
