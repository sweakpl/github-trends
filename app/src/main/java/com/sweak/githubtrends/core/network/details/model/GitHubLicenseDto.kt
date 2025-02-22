package com.sweak.githubtrends.core.network.details.model

import com.google.gson.annotations.SerializedName

data class GitHubLicenseDto(
    @SerializedName("spdx_id") val spdxId: String
)