package com.noelpena.featureflagservice.service

import com.noelpena.featureflagservice.dto.CreateFeatureFlagRequest
import com.noelpena.featureflagservice.dto.FeatureFlagResponse
import com.noelpena.featureflagservice.model.FeatureFlag
import com.noelpena.featureflagservice.repository.FeatureFlagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class FeatureFlagService(
    private val repository: FeatureFlagRepository
) {
    fun getAllFeatureFlags(): List<FeatureFlagResponse> {
        return repository.findAll().map { FeatureFlagResponse(
            it.id!!,
            it.key,
            it.description,
            it.isEnabled,
            it.createdAt
        )}
    }

    @Transactional
    fun createFeatureFlag(request: CreateFeatureFlagRequest): FeatureFlagResponse {
        if (repository.findByKey(request.key) != null) {
            throw IllegalArgumentException("Feature flag with key ${request.key} already exists")
        }

        val featureFlag = FeatureFlag(
            key = request.key,
            description = request.description,
            isEnabled = request.isEnabled
        )

        val savedFlag = repository.save(featureFlag)

        return FeatureFlagResponse(
            id = savedFlag.id!!,
            key = savedFlag.key,
            description = savedFlag.description,
            isEnabled = savedFlag.isEnabled,
            createdAt = savedFlag.createdAt
        )
    }

    @Transactional
    fun toggleFeatureFlag(id: UUID): FeatureFlagResponse {
        val featureFlag = repository.findById(id).orElseThrow {
            IllegalArgumentException("Feature flag with id $id not found")
        }

        featureFlag.isEnabled = !featureFlag.isEnabled

        val savedFlag = repository.save(featureFlag)

        return FeatureFlagResponse(
            id = savedFlag.id!!,
            key = savedFlag.key,
            description = savedFlag.description,
            isEnabled = savedFlag.isEnabled,
            createdAt = savedFlag.createdAt
        )
    }
}