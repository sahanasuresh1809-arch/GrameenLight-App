package com.example.grameenlight.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ComplaintRepository(
    private val dao: ComplaintDao
) {
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("complaints")

    init {
        // 🔥 REAL-TIME CLOUD-TO-LOCAL SYNC
        // This listens to Firestore and updates Room whenever data changes in the cloud.
        collection.addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("FIRESTORE_SYNC", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshots != null) {
                val complaints = snapshots.toObjects(ComplaintEntity::class.java)
                // Update local Room database with Cloud data
                CoroutineScope(Dispatchers.IO).launch {
                    complaints.forEach { complaint ->
                        dao.insertComplaint(complaint)
                    }
                }
            }
        }
    }

    fun getAllComplaints(): Flow<List<ComplaintEntity>> {
        return dao.getAllComplaints()
    }

    suspend fun addComplaint(complaint: ComplaintEntity) {
        // 1. Save to Room (Local)
        dao.insertComplaint(complaint)
        
        // 2. Sync to Firestore (Cloud)
        try {
            collection.document(complaint.id).set(complaint).await()
            Log.d("FIRESTORE_SYNC", "Complaint added to Cloud")
        } catch (e: Exception) {
            Log.e("FIRESTORE_SYNC", "Error syncing to Cloud: ${e.message}")
        }
    }

    suspend fun updateComplaint(complaint: ComplaintEntity) {
        dao.updateComplaint(complaint)
        try {
            collection.document(complaint.id).set(complaint).await()
        } catch (e: Exception) {
            Log.e("FIRESTORE_SYNC", "Error syncing update: ${e.message}")
        }
    }

    suspend fun updateStatus(id: String, status: String) {
        // 1. Update Room
        dao.updateComplaintStatus(id, status)
        
        // 2. Sync Status to Firestore
        try {
            collection.document(id).update("status", status).await()
            Log.d("FIRESTORE_SYNC", "Status synced to Cloud")
        } catch (e: Exception) {
            Log.e("FIRESTORE_SYNC", "Error syncing status: ${e.message}")
        }
    }

    suspend fun deleteComplaint(complaint: ComplaintEntity) {
        dao.deleteComplaint(complaint)
        try {
            collection.document(complaint.id).delete().await()
        } catch (e: Exception) {
            Log.e("FIRESTORE_SYNC", "Error syncing delete: ${e.message}")
        }
    }
}
