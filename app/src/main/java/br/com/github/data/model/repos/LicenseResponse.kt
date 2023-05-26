package br.com.github.data.model.repos

import br.com.github.data.base.Mapper
import br.com.github.domain.model.repos.LicenseModel
import com.google.gson.annotations.SerializedName

data class LicenseResponse(
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("spdx_id")
    val spdxId: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("node_id")
    val nodeId: String?
) : Mapper<LicenseModel> {
    override fun mapTo() = LicenseModel(
        key = key,
        name = name,
        spdxId = spdxId,
        url = url,
        nodeId = nodeId
    )
}
