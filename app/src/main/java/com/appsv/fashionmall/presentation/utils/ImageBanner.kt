package com.appsv.fashionmall.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.appsv.fashionmall.R
import com.appsv.fashionmall.domain.models.BannerDataModels
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Banner(banners: List<BannerDataModels>) {

    val pagerState = rememberPagerState(pageCount = { banners.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % banners.size

            scope.launch {
                pagerState.animateScrollToPage(nextPage)
            }

        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.wrapContentSize()

            ) { selectedPage ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .padding(top = 8.dp, start = 15.dp, end = 15.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    AsyncImage(
                        model = banners[selectedPage].image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }

            }
        }
        PageIndicator(
            pageCount = banners.size,
            selectedPage = pagerState.currentPage,
            modifier = Modifier
        )
    }
}

@Composable
fun SelectedDot(modifier: Modifier) {

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .padding(2.dp)
            .height(10.dp)
            .width(28.dp)
            .background(
                colorResource(id = R.color.button_color).copy(alpha = 0.8f),
                shape = RoundedCornerShape(5.dp)
            )
    )

}

@Composable
fun IndicatorDot(isSelected: Boolean, modifier: Modifier) {

    if (isSelected) {
        SelectedDot(modifier)
    } else {
        Box(
            modifier = modifier
                .clip(shape = CircleShape)
                .padding(2.dp)
                .size(8.dp)
                .background(
                    color = colorResource(id = R.color.button_color).copy(alpha = 0.5f),
                    shape = CircleShape
                )
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, selectedPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(top = 3.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        repeat(pageCount) {
            IndicatorDot(isSelected = it == selectedPage, modifier = modifier)
        }
    }
}

