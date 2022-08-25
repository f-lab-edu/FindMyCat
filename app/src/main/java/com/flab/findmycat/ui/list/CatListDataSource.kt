package com.flab.findmycat.ui.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.network.CatsApiService
import retrofit2.HttpException
import java.io.IOException

const val INITIAL_PAGE_INDEX = 0
const val NETWORK_PAGE_SIZE = 25

open class CatListDataSource(
    private val catApiService: CatsApiService
) : PagingSource<Int, Cat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        val pageIndex = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = catApiService.getCats(page = pageIndex, limit = 10)

            val nextKey =
                if (response.isEmpty()) {
                    null
                } else {
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }

            LoadResult.Page(
                data = response,
                prevKey = if (pageIndex == INITIAL_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (execption: IOException) {
            return LoadResult.Error(execption)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}