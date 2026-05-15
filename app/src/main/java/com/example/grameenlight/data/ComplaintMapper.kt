package com.example.grameenlight.data

fun ComplaintEntity.toComplaint(): Complaint {
    return Complaint(
        id = id,
        pole = pole,
        issue = issue,
        status = status,
        date = date,
        note = note
    )
}

fun Complaint.toEntity(): ComplaintEntity {
    return ComplaintEntity(
        id = id,
        pole = pole,
        issue = issue,
        status = status,
        date = date,
        note = note
    )
}