package com.flab.findmycat.data.database.model

import androidx.room.Embedded

data class RepoWithOwner(
    @Embedded val repo: Repo,
    @Embedded val owner: Owner
)
