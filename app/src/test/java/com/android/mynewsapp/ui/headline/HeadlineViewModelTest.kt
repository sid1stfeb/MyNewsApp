package com.android.mynewsapp.ui.headline

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.mynewsapp.data.model.NewsList
import com.android.mynewsapp.data.network.DataHandler
import com.android.mynewsapp.network.FakeApiHelperImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class HeadlineViewModelTest{

    @Mock
    private lateinit var viewModel: HeadlineViewModel

    @Mock
    private lateinit var observer: Observer<in DataHandler<NewsList>>


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        viewModel = spy(HeadlineViewModel(FakeApiHelperImpl()))
    }

    @Test
    fun `Verify livedata values changes on event`() {
        viewModel.getNews(2)
        viewModel.headlines.observeForever(observer)
        verify(observer).onChanged(DataHandler.success(FakeApiHelperImpl.success2().body()))
        viewModel.getNews(1)
        verify(observer).onChanged(DataHandler.success(FakeApiHelperImpl.success().body()))
        viewModel.headlines.removeObserver(observer)

    }
}