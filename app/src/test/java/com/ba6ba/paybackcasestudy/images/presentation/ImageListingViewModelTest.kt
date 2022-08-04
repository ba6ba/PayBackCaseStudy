package com.ba6ba.paybackcasestudy.images.presentation

import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.*
import com.ba6ba.paybackcasestudy.images.data.ImageDetailArgsData
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData
import com.ba6ba.paybackcasestudy.images.domain.FetchSavedQueryUseCase
import com.ba6ba.paybackcasestudy.images.domain.ImageListingUseCase
import com.ba6ba.paybackcasestudy.images.domain.Images
import com.ba6ba.paybackcasestudy.images.domain.RefreshSearchUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class ImageListingViewModelTest : BaseTest() {

    @Mock
    lateinit var lightDarkModeManager: LightDarkModeManager

    @Mock
    lateinit var imageListingUseCase: ImageListingUseCase

    @Mock
    lateinit var imageUiDataTransformer: ImageUiDataTransformer

    @Mock
    lateinit var refreshSearchUseCase: RefreshSearchUseCase

    @Mock
    lateinit var fetchSavedQueryUseCase: FetchSavedQueryUseCase

    private lateinit var imageListingViewModel: ImageListingViewModel

    @Before
    fun setUp() {
        imageListingViewModel =
            ImageListingViewModel(
                lightDarkModeManager,
                imageListingUseCase,
                imageUiDataTransformer,
                refreshSearchUseCase,
                fetchSavedQueryUseCase
            )
    }

    @Test
    fun getArgsData() {
        val imageItemUiData = ImageItemUiData(
            imageUrl = "preview url",
            userName = "user name",
            tags = emptyList(),
            metadata = ImageItemUiData.Metadata(
                hdImageUrl = "hd image url",
                comments = 499L,
                likes = 13L,
                downloads = 1L,
                tags = "fruits, veg, food"
            )
        )
        val imageDetailArgsData = ImageDetailArgsData(
            hdImageUrl = imageItemUiData.metadata.hdImageUrl,
            previewUrl = imageItemUiData.imageUrl,
            userName = imageItemUiData.userName,
            likes = imageItemUiData.metadata.likes,
            comments = imageItemUiData.metadata.comments,
            downloads = imageItemUiData.metadata.downloads,
            tags = imageItemUiData.metadata.tags
        )
        val actualArgsData = imageListingViewModel.getArgsData(imageItemUiData)

        assertEquals(imageDetailArgsData, actualArgsData)
    }

    @Test
    fun `verify paging data flow`() = runTest {
        val query = "fruits"
        val images = Images(
            1L,
            "fruits, veg",
            EMPTY_STRING,
            EMPTY_STRING,
            0L,
            0L,
            0L,
            EMPTY_STRING
        )
        val pagingSource: PagingSource<Int, Images> = object : PagingSource<Int, Images>() {
            override fun getRefreshKey(state: PagingState<Int, Images>): Int? {
                return null
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Images> {
                return LoadResult.Page(
                    data = images.createList(Constants.PAGE_LIMIT),
                    nextKey = 2,
                    prevKey = null
                )
            }
        }
        whenever(imageListingUseCase(query)).thenReturn(pagingSource)
        imageListingViewModel.onQueryTextSubmit.onSubmit(query)

        val pagingData = imageListingViewModel.pagingData.take(1).firstOrNull()
        advanceUntilIdle()

        assertNotNull(pagingData)
        verify(refreshSearchUseCase).invoke()
    }

    @Test
    fun `verify set persisted display mode for night`() = runTest {
        val query = "fruits"
        whenever(lightDarkModeManager.isDarkModeEnabled()).thenReturn(true)
        whenever(fetchSavedQueryUseCase.invoke()).thenReturn(query)

        imageListingViewModel.onInit()
        advanceUntilIdle()

        verify(lightDarkModeManager).setCurrentMode()
        assertEquals(imageListingViewModel.dayNightIconResource.value, R.drawable.ic_night_mode)
        assertEquals(imageListingViewModel.onQueryTextChange.value, query)
    }

    @Test
    fun `verify set persisted display mode for day`() = runTest {
        val query = "fruits"
        whenever(lightDarkModeManager.isDarkModeEnabled()).thenReturn(false)
        whenever(fetchSavedQueryUseCase.invoke()).thenReturn(query)

        imageListingViewModel.onInit()
        advanceUntilIdle()

        verify(lightDarkModeManager).setCurrentMode()
        assertEquals(imageListingViewModel.dayNightIconResource.value, R.drawable.ic_day_mode)
        assertEquals(imageListingViewModel.onQueryTextChange.value, query)
    }

    @Test
    fun `verify on day night button click and validate for day mode`() = runTest {
        whenever(lightDarkModeManager.isDarkModeEnabled()).thenReturn(false)

        imageListingViewModel.onDayNightButtonClick()

        verify(lightDarkModeManager).toggle()
        assertEquals(imageListingViewModel.dayNightIconResource.value, R.drawable.ic_day_mode)
    }

    @Test
    fun `verify on day night button click and validate for night mode`() = runTest {
        whenever(lightDarkModeManager.isDarkModeEnabled()).thenReturn(true)

        imageListingViewModel.onDayNightButtonClick()

        verify(lightDarkModeManager).toggle()
        assertEquals(imageListingViewModel.dayNightIconResource.value, R.drawable.ic_night_mode)
    }

    @Test
    fun `verify refresh`() = runTest {
        imageListingViewModel.onRefresh.onSwipeRefresh()
        val actualRefreshValue = imageListingViewModel.refreshAdapter.first()
        advanceUntilIdle()

        verify(refreshSearchUseCase).invoke()
        assertEquals(actualRefreshValue, Unit)
    }

    @Test
    fun `verify search query submit`() = runTest {
        val query = "fruits"

        imageListingViewModel.onQueryTextSubmit.onSubmit(query)
        advanceUntilIdle()

        verify(refreshSearchUseCase).invoke()
        assertEquals(imageListingViewModel.onQueryTextChange.value, query)
    }

    @Test
    fun `verify search query submit but on interaction`() = runTest {
        val query = EMPTY_STRING

        imageListingViewModel.onQueryTextSubmit.onSubmit(query)

        verifyNoMoreInteractions(refreshSearchUseCase)
    }

    @Test
    fun `verify view state on process combined state for loading`() {
        val refreshLoadState = LoadState.Loading
        val appendLoadState = LoadState.Loading
        val prependLoadState = LoadState.Loading
        imageListingViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(imageListingViewModel.viewStateFlow.value, ViewState.Loading)
    }

    @Test
    fun `verify view state on process combined state for error`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.Error(Throwable(message))
        val appendLoadState = LoadState.Error(Throwable(message))
        val prependLoadState = LoadState.Error(Throwable(message))
        imageListingViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(
            imageListingViewModel.viewStateFlow.value,
            ViewState.Error(UiError(message = message))
        )
    }

    @Test
    fun `verify view state on process combined state for error for refresh load state`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.Error(Throwable(message))
        val appendLoadState = LoadState.Loading
        val prependLoadState = LoadState.Loading
        imageListingViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(LoadState.Loading, LoadState.Loading, LoadState.Loading)
            )
        )

        assertEquals(
            imageListingViewModel.viewStateFlow.value,
            ViewState.Error(UiError(message = message))
        )
    }

    @Test
    fun `verify view state on process combined state for success`() {
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val prependLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        imageListingViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(imageListingViewModel.viewStateFlow.value, ViewState.Success(Unit))
    }

    @Test
    fun `verify view state on process combined state for error for prepend`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val prependLoadState = LoadState.Error(Throwable(message))
        imageListingViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(
            imageListingViewModel.viewStateFlow.value,
            ViewState.Error(UiError(message = message))
        )
    }

    @Test
    fun `verify view state on process combined state for error for append`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.Error(Throwable(message))
        val prependLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        imageListingViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(
            imageListingViewModel.viewStateFlow.value,
            ViewState.Error(UiError(message = message))
        )
    }

    @Test
    fun `verify view state on process combined state for error for source-append`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val prependLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        imageListingViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(
                    refreshLoadState,
                    prependLoadState,
                    LoadState.Error(Throwable(message))
                )
            )
        )

        assertEquals(
            imageListingViewModel.viewStateFlow.value,
            ViewState.Error(UiError(message = message))
        )
    }

    @Test
    fun `verify view state on process combined state for error for source-prepend`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val prependLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        imageListingViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(
                    refreshLoadState,
                    LoadState.Error(Throwable(message)),
                    appendLoadState
                )
            )
        )

        assertEquals(
            imageListingViewModel.viewStateFlow.value,
            ViewState.Error(UiError(message = message))
        )
    }
}