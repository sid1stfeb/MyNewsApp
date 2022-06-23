package com.android.mynewsapp.other

import android.widget.ImageView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.mynewsapp.other.Util.getReadableDate
import com.android.mynewsapp.other.Util.loadImage
import com.android.mynewsapp.other.Util.shareNews
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UtilTest{

    private val randomString = "random string"
    private val correctDate = "2022-01-01T16:03:35Z"
    private val correctDateResult = "01 Jan 2022 at 04:03 PM"

    @Test
    fun `wrong input date`(){
        assertEquals(getReadableDate(randomString), "")
    }

    @Test
    fun `input date as null`(){
        assertEquals(getReadableDate(null), "")
    }

    @Test
    fun `correct input date`(){
        assertEquals(getReadableDate(correctDate), correctDateResult)
    }

    @Test
    fun `passing null url`(){
        assertTrue( try{
            loadImage(ApplicationProvider.getApplicationContext(), null, ImageView(ApplicationProvider.getApplicationContext()))
            true
        }catch(ex:Exception){
            false
        })
    }

    @Test
    fun `passing random message to share`(){
        assertTrue( try{
            shareNews(ApplicationProvider.getApplicationContext(), randomString)
            true
        }catch(ex:Exception){
            false
        })
    }


}