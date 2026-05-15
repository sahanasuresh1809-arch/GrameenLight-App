package com.example.grameenlight.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.grameenlight.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ComplaintViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = ComplaintRepository(db.complaintDao())

    // 🔥 LIVE DATA FROM ROOM
    val complaints: StateFlow<List<ComplaintEntity>> =
        repository.getAllComplaints()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // 🔥 ADD COMPLAINT
    fun addComplaint(pole: String, issue: String, note: String = "") {

        viewModelScope.launch {

            val complaint = ComplaintEntity(
                id = "GL-${System.currentTimeMillis().toString().takeLast(6)}",
                pole = pole,
                issue = issue,
                status = "Reported",
                date = java.text.SimpleDateFormat("dd MMM yyyy • hh:mm a", java.util.Locale.getDefault())
                    .format(java.util.Date()),
                note = note
            )

            repository.addComplaint(complaint)
        }
    }

    // 🔥 UPDATE STATUS (Optimized)
    fun updateStatus(id: String, status: String) {
        viewModelScope.launch {
            repository.updateStatus(id, status)
        }
    }

    // 🔥 DELETE
    fun deleteComplaint(complaint: ComplaintEntity) {
        viewModelScope.launch {
            repository.deleteComplaint(complaint)
        }
    }
}
