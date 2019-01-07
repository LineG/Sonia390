package com.fsck.k9.preferences.migrations

import android.database.sqlite.SQLiteDatabase

internal object StorageMigrations {
    @JvmStatic
    fun upgradeDatabase(db: SQLiteDatabase, migrationsHelper: StorageMigrationsHelper) {
        val oldVersion = db.version

        if (oldVersion <= 1) StorageMigrationTo2.urlEncodeUserNameAndPassword(db, migrationsHelper)
        if (oldVersion <= 2) StorageMigrationTo3(db, migrationsHelper).rewriteFolderNone()
        if (oldVersion <= 3) StorageMigrationTo4(db, migrationsHelper).insertSpecialFolderSelectionValues()
        if (oldVersion <= 4) StorageMigrationTo5(db, migrationsHelper).fixMailCheckFrequencies()
    }
}
