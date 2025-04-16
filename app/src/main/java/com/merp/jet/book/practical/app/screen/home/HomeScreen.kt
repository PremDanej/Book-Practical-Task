package com.merp.jet.book.practical.app.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.merp.jet.book.practical.app.R
import com.merp.jet.book.practical.app.components.VerticalSpace
import com.merp.jet.book.practical.app.model.Element
import com.merp.jet.book.practical.app.model.MediaData
import com.merp.jet.book.practical.app.viewmodel.BookDiscoveryViewModel

// Elements types
enum class ELEMENTS {
    BANNER,
    CLASSIC,
    FEATURED,
    CAROUSEL,
    GROUP_CONTENT
}

// FontFamily same as Figma
val font = Font(R.font.sf_pro_regural, weight = FontWeight.W600)
val preFontFamily = FontFamily(font)

val newYorkFont = Font(R.font.new_york)
val newYorkFontFamily = FontFamily(newYorkFont)

// Color code takes from Figma
val cardColor = Color(0xFFF5F5F5)
val buttonOutlineColor = Color(0xFFD26E09)
val textColor = Color(0xFF0F8079)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: BookDiscoveryViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getDiscoveryItems()
    }

    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullToRefreshState( )

    val bannerElement by lazy { MutableLiveData<Element>() }
    val carouselElement by lazy { MutableLiveData<Element>() }
    val classicElement by lazy { MutableLiveData<Element>() }
    val featuredElement by lazy { MutableLiveData<Element>() }
    val groupContentElement by lazy { MutableLiveData<Element>() }

    if (viewModel.isLoading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    } else {
        viewModel.item.value?.let { discoveryItem ->
            if (discoveryItem.success) {
                discoveryItem.page.elements.map { element ->
                    when (element.element_type.uppercase()) {
                        ELEMENTS.BANNER.name -> bannerElement.value = element
                        ELEMENTS.CAROUSEL.name -> carouselElement.value = element
                        ELEMENTS.CLASSIC.name -> classicElement.value = element
                        ELEMENTS.FEATURED.name -> featuredElement.value = element
                        ELEMENTS.GROUP_CONTENT.name -> groupContentElement.value = element
                    }
                }
            } else {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .pullToRefresh( // Swipe Pull request added
                    isRefreshing = refreshing,
                    state = pullRefreshState,
                    onRefresh = {
                        viewModel.getDiscoveryItems()
                    },
                )
        ) {
            item {
                bannerElement.value?.let { DisplayBanner(it) }
                carouselElement.value?.let { DisplayCarousel(it) }
                classicElement.value?.let { DisplayClassic(it) }
                featuredElement.value?.let { DisplayFeatured(it) }
                groupContentElement.value?.let { DisplayGroupContent(it) }
            }
        }
    }
}

@Composable // Header with extra data
fun HeaderWithSeeAll(title: String, isExtra: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title, fontFamily = newYorkFontFamily,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 28.sp,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.weight(1f)
        )
        if (isExtra) {
            Text(text = "See all", color = textColor, fontWeight = FontWeight.Bold)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "icon",
                tint = textColor
            )
        }
    }
}

// Display Banner Element Type
@Composable
fun DisplayBanner(element: Element) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = element.mobile_image_url,
                imageLoader = ImageLoader(LocalContext.current)
            ), contentDescription = "Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomCenter)
        )
    }

    VerticalSpace(value = 20)
}

// Display Carousel Element Type
@Composable
fun DisplayCarousel(element: Element) {
    Column {
        Text(
            element.header.uppercase(),
            fontFamily = preFontFamily,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 18.sp,
                letterSpacing = 1.5.sp
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 5.dp)
        )
        Text(
            "\"This book changed my life. I cannot recommend it enough.\"",
            fontFamily = newYorkFontFamily,
            fontStyle = FontStyle.Italic,
            style = TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                letterSpacing = 1.sp
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 5.dp)
        )
        LazyRow(
            modifier = Modifier.height(240.dp),
            contentPadding = PaddingValues(6.dp)
        ) {
            items(3) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = element.component_items[0].image_url,
                        imageLoader = ImageLoader(LocalContext.current)
                    ), contentDescription = "Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(390.dp)
                        .fillMaxHeight()
                        .padding(horizontal = 6.dp)
                        .clip(MaterialTheme.shapes.large)
                )
            }
        }
    }

    VerticalSpace(value = 16)
}

// Display Classic Element Type
@Composable
fun DisplayClassic(element: Element) {

    HeaderWithSeeAll(title = "Recommended for you", isExtra = true)
    Text(
        text = "Based on your recent activity",
        fontSize = 14.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )

    VerticalSpace(value = 16)

    LazyRow(
        modifier = Modifier.height(200.dp),
        contentPadding = PaddingValues(6.dp)
    ) {
        items(5) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = element.component_items[0].media_data.cover.listing_url,
                    imageLoader = ImageLoader(LocalContext.current)
                ), contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .padding(horizontal = 6.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }

    VerticalSpace(value = 16)
}

// Display Featured Element Type
@Composable
fun DisplayFeatured(element: Element) {

    HeaderWithSeeAll(title = "Popular in Bookshelf", isExtra = false)

    VerticalSpace(value = 16)

    LazyRow(
        modifier = Modifier.height(430.dp),
        contentPadding = PaddingValues(6.dp)
    ) {
        items(5) {
            FeaturedItemData(element.component_items[0].media_data)
        }
    }

    VerticalSpace(value = 16)

}

// Featured Elements Item/Children
@Composable
fun FeaturedItemData(mediaData: MediaData) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .padding(horizontal = 6.dp),
        color = cardColor,
        shadowElevation = 1.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = mediaData.cover.listing_url,
                    imageLoader = ImageLoader(LocalContext.current)
                ), contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(210.dp)
                    .padding(bottom = 10.dp)
                    .clip(MaterialTheme.shapes.large)
            )

            Text(
                text = mediaData.title,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.sp
                ),
            )

            Text(
                text = mediaData.description.split("<p>")[1],
                maxLines = 4,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier.alpha(0.65f)
            )


            OutlinedButton(
                onClick = { },
                border = BorderStroke(width = 1.dp, color = buttonOutlineColor)
            ) {
                Text(text = "Listen now", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Display GroupContent Element Type
@Composable
fun DisplayGroupContent(element: Element) {

    HeaderWithSeeAll(title = "New Audiobooks", isExtra = true)

    VerticalSpace(value = 16)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardColor, MaterialTheme.shapes.large)
        ) {
            repeat(3) {
                GroupContentItemData(element.component_items[0].media_data)
            }
        }
    }

    VerticalSpace(value = 16)

}

// GroupContent Elements Item/Children
@Composable
fun GroupContentItemData(mediaData: MediaData) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = mediaData.cover.thumbnail_url,
                imageLoader = ImageLoader(LocalContext.current)
            ),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.large)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = mediaData.title,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 17.sp,
                    letterSpacing = 1.sp
                )
            )

            VerticalSpace(4)

            val authorName = mediaData.authors[0]
            Text(
                text = "${authorName.first_name} ${authorName.last_name}",
                maxLines = 1,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier.alpha(0.65f)
            )
        }
    }
}
