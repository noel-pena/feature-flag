package com.noelpena.featureflagservice.service

import com.noelpena.featureflagservice.dto.CreateFeatureFlagRequest
import com.noelpena.featureflagservice.dto.FeatureFlagResponse
import com.noelpena.featureflagservice.model.FeatureFlag
import com.noelpena.featureflagservice.repository.FeatureFlagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FeatureFlagService(
    private val repository: FeatureFlagRepository
) {
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

    fun getAllFeatureFlags(): List<FeatureFlagResponse> {
        return repository.findAll().map { FeatureFlagResponse(
            it.id!!,
            it.key,
            it.description,
            it.isEnabled,
            it.createdAt
        )}
    }
}