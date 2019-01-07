package com.fsck.k9.mailstore

import android.content.ContentValues
import com.fsck.k9.Clock

class OutboxStateRepository(private val database: LockableDatabase, private val clock: Clock) {

    fun getOutboxState(messageId: Long): OutboxState {
        return database.execute(false) { db ->
            db.query(
                    TABLE_NAME,
                    COLUMNS,
                    "$COLUMN_MESSAGE_ID = ?",
                    arrayOf(messageId.toString()), null, null, null
            ).use { cursor ->
                if (!cursor.moveToFirst()) {
                    throw IllegalStateException("No outbox_state entry for message with id $messageId")
                }

                val sendStateString = cursor.getString(cursor.getColumnIndex(COLUMN_SEND_STATE))
                val numberOfSendAttempts = cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_OF_SEND_ATTEMPTS))
                val sendErrorTimestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_ERROR_TIMESTAMP))
                val sendErrorColumnIndex = cursor.getColumnIndex(COLUMN_ERROR)
                val sendError = if (cursor.isNull(sendErrorColumnIndex)) null else cursor.getString(sendErrorColumnIndex)

                val sendState = SendState.fromDatabaseName(sendStateString)

                OutboxState(sendState, numberOfSendAttempts, sendError, sendErrorTimestamp)
            }
        }
    }

    fun initializeOutboxState(messageId: Long) {
        database.execute(false) { db ->
            val contentValues = ContentValues().apply {
                put(COLUMN_MESSAGE_ID, messageId)
                put(COLUMN_SEND_STATE, SendState.READY.databaseName)
            }

            db.insert(TABLE_NAME, null, contentValues)
        }
    }

    fun removeOutboxState(messageId: Long) {
        database.execute(false) { db ->
            db.delete(TABLE_NAME, "$COLUMN_MESSAGE_ID = ?", arrayOf(messageId.toString()))
        }
    }

    fun incrementSendAttempts(messageId: Long) {
        database.execute(false) { db ->
            db.execSQL("UPDATE $TABLE_NAME " +
                    "SET $COLUMN_NUMBER_OF_SEND_ATTEMPTS = $COLUMN_NUMBER_OF_SEND_ATTEMPTS + 1 " +
                    "WHERE $COLUMN_MESSAGE_ID = ?",
                    arrayOf(messageId.toString())
            )
        }
    }

    fun decrementSendAttempts(messageId: Long) {
        database.execute(false) { db ->
            db.execSQL("UPDATE $TABLE_NAME " +
                    "SET $COLUMN_NUMBER_OF_SEND_ATTEMPTS = $COLUMN_NUMBER_OF_SEND_ATTEMPTS - 1 " +
                    "WHERE $COLUMN_MESSAGE_ID = ?",
                    arrayOf(messageId.toString())
            )
        }
    }

    fun setSendAttemptError(messageId: Long, errorMessage: String) {
        val sendErrorTimestamp = clock.time

        database.execute(false) { db ->
            val contentValues = ContentValues().apply {
                put(COLUMN_SEND_STATE, SendState.ERROR.databaseName)
                put(COLUMN_ERROR_TIMESTAMP, sendErrorTimestamp)
                put(COLUMN_ERROR, errorMessage)
            }

            db.update(TABLE_NAME, contentValues, "$COLUMN_MESSAGE_ID = ?", arrayOf(messageId.toString()))
        }
    }

    fun setSendAttemptsExceeded(messageId: Long) {
        val sendErrorTimestamp = clock.time

        database.execute(false) { db ->
            val contentValues = ContentValues().apply {
                put(COLUMN_SEND_STATE, SendState.RETRIES_EXCEEDED.databaseName)
                put(COLUMN_ERROR_TIMESTAMP, sendErrorTimestamp)
                putNull(COLUMN_ERROR)
            }

            db.update(TABLE_NAME, contentValues, "$COLUMN_MESSAGE_ID = ?", arrayOf(messageId.toString()))
        }
    }


    companion object {
        private const val TABLE_NAME = "outbox_state"
        private const val COLUMN_MESSAGE_ID = "message_id"
        private const val COLUMN_SEND_STATE = "send_state"
        private const val COLUMN_NUMBER_OF_SEND_ATTEMPTS = "number_of_send_attempts"
        private const val COLUMN_ERROR_TIMESTAMP = "error_timestamp"
        private const val COLUMN_ERROR = "error"

        private val COLUMNS = arrayOf(
                COLUMN_SEND_STATE,
                COLUMN_NUMBER_OF_SEND_ATTEMPTS,
                COLUMN_ERROR_TIMESTAMP,
                COLUMN_ERROR
        )
    }
}
