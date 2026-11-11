package com.example.progr3ss.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class HabitCategory(
    val id: Long,
    val name: String,
    val iconUrl: String? = null
)

data class HabitResponse(
    val id: Long,
    val name: String,
    val description: String? = null,
    val category: HabitCategory? = null,
    val goal: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null
)

data class ScheduleResponse(
    val id: Long,
    @SerializedName("start_time") val startTime: LocalDateTime? = null,
    @SerializedName("end_time") val endTime: LocalDateTime? = null,
    val status: String? = null,
    val date: String? = null,
    @SerializedName("is_custom") val isCustom: Boolean = false,
    @SerializedName("created_at") val createdAt: LocalDateTime? = null,
    @SerializedName("updated_at") val updatedAt: LocalDateTime? = null,
    val type: String? = null,
    @SerializedName("duration_minutes") val durationMinutes: Int? = null,
    val notes: String? = null,
    val participants: List<ParticipantDto>? = emptyList(),
    val habit: HabitResponse? = null,
    val progress: List<ProgressResponseDto>? = emptyList(),
    @SerializedName("is_participant_only") val isParticipantOnly: Boolean = false
)

data class ProgressResponseDto(
    val id: Long,
    val scheduleId: Long? = null,
    val date: String? = null,
    @SerializedName("logged_time") val loggedTime: Double? = null,
    val notes: String? = null,
    @SerializedName("is_completed") val isCompleted: Boolean = false
)

data class ParticipantDto(
    val id: Long,
    val name: String? = null,
    val email: String? = null,
    @SerializedName("profile_image") val profileImage: String? = null
)