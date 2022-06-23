package com.android.mynewsapp.ui.detailnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.mynewsapp.data.network.FakeData
import com.android.mynewsapp.other.Util
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class DetailNewsViewModelTest{
    private val testItem = FakeData.newsList[4]
    private val testItem2 = FakeData.newsList[3]

    @Mock
    private lateinit var viewModel:DetailNewsViewModel

    @Mock
    private lateinit var observer: Observer<in String?>


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        viewModel = spy(DetailNewsViewModel())
    }

    @Test
    fun `Verify livedata headline values changes on event`() {
        viewModel.newsItem(testItem)
        viewModel.headline.observeForever(observer)
        verify(observer).onChanged(testItem.title)
        viewModel.newsItem(testItem2)
        verify(observer).onChanged(testItem2.title)
        viewModel.headline.removeObserver(observer)

    }

    @Test
    fun `Verify livedata detail values changes on event`() {
        viewModel.newsItem(testItem)
        viewModel.detail.observeForever(observer)
        verify(observer).onChanged(testItem.description)
        viewModel.newsItem(testItem2)
        verify(observer).onChanged(testItem2.description)
        viewModel.detail.removeObserver(observer)

    }

    @Test
    fun `Verify livedata time values changes on event`() {
        viewModel.newsItem(testItem)
        viewModel.time.observeForever(observer)
        verify(observer).onChanged(Util.getReadableDate(testItem.publishedAt))
        viewModel.newsItem(testItem2)
        verify(observer).onChanged(Util.getReadableDate(testItem2.publishedAt))
        viewModel.time.removeObserver(observer)

    }

    @Test
    fun `Verify livedata author values changes on event`() {
        viewModel.newsItem(testItem)
        viewModel.author.observeForever(observer)
        verify(observer).onChanged(testItem.author)
        viewModel.newsItem(testItem2)
        verify(observer).onChanged(testItem2.author)
        viewModel.author.removeObserver(observer)

    }

    @Test
    fun `Verify livedata image values changes on event`() {
        viewModel.newsItem(testItem)
        viewModel.image.observeForever(observer)
        verify(observer).onChanged(testItem.urlToImage)
        viewModel.newsItem(testItem2)
        verify(observer).onChanged(testItem2.urlToImage)
        viewModel.image.removeObserver(observer)

    }
}