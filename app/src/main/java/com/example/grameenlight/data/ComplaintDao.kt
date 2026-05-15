package com.example.grameenlight.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ComplaintDao {

    @Query("SELECT * FROM complaints ORDER BY date DESC")
    fun getAllComplaints(): Flow<List<ComplaintEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComplaint(complaint: ComplaintEntity)

    @Update
    suspend fun updateComplaint(complaint: ComplaintEntity)

    @Query("UPDATE complaints SET status = :status WHERE id = :id")
    suspend fun updateComplaintStatus(id: String, status: String)

    @Delete
    suspend fun deleteComplaint(complaint: ComplaintEntity)
}