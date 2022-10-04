package com.flab.findmycat.data.network.response

import com.flab.findmycat.data.database.model.Repo

data class SearchResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<Repo>
) : BaseResponse<Any?, Any?>
