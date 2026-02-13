package com.noelpena.featureflagservice.controller

import com.noelpena.featureflagservice.dto.CreateFeatureFlagRequest
import com.noelpena.featureflagservice.dto.FeatureFlagResponse
import com.noelpena.featureflagservice.service.FeatureFlagService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/feature-flags")
@CrossOrigin(origins = ["http://localhost:3000"])
class FeatureFlagController(
    private val service: FeatureFlagService
) {
    @GetMapping
    fun getAllFlags(): List<FeatureFlagResponse> {
        return service.getAllFeatureFlags()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createFlag(@RequestBody request: CreateFeatureFlagRequest): FeatureFlagResponse {
        return service.createFeatureFlag(request)
    }

    @PatchMapping("/{id}/toggle")
    fun toggleFlag(@PathVariable id: UUID): FeatureFlagResponse {
        return service.toggleFeatureFlag(id)
    }
}