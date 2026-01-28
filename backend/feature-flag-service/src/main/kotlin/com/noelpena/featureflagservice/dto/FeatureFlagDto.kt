package com.noelpena.featureflagservice.dto

import java.time.Instant
import java.util.UUID

data class CreateFeatureFlagRequest(
    val key: String,
    val description: String,
    val isEnabled: Boolean
)

data class FeatureFlagResponse(
    val id: UUID,
    val key: String,
    val description: String,
    val isEnabled: Boolean,
    val createdAt: Instant?
)