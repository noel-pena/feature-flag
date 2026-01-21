package com.noelpena.featureflagservice.repository

import com.noelpena.featureflagservice.model.FeatureFlag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FeatureFlagRepository: JpaRepository<FeatureFlag, UUID> {
    fun findByKey(key: String): FeatureFlag?
}