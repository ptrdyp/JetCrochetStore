package com.dicoding.jetcrochetstore.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.jetcrochetstore.R
import com.dicoding.jetcrochetstore.di.Injection
import com.dicoding.jetcrochetstore.ui.ViewModelFactory
import com.dicoding.jetcrochetstore.ui.common.UiState
import com.dicoding.jetcrochetstore.ui.components.OrderButton
import com.dicoding.jetcrochetstore.ui.components.ProductCounter

@Composable
fun DetailScreen(
    crochetId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCrochetById(crochetId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.crochet.image,
                    title = data.crochet.title,
                    category = data.crochet.category,
                    description = data.crochet.description,
                    basePrice = data.crochet.price,
                    count = data.count,
                    onBackClick = navigateBack,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.crochet, count)
                        navigateToCart()
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    image: String,
    title: String,
    category: String,
    description: String,
    basePrice: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var totalPrice by rememberSaveable {
        mutableStateOf(0)
    }
    var orderCount by rememberSaveable {
        mutableStateOf(count)
    }

    Column(modifier = modifier) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    modifier = modifier
                        .padding(
                            top = 16.dp
                        )
                )
                Text(
                    text = stringResource(R.string.price, basePrice),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = modifier
                        .padding(
                            top = 16.dp
                        )
                )
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    textAlign = TextAlign.Justify,
                    modifier = modifier
                        .padding(
                            top = 32.dp
                        )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = modifier
                        .padding(
                            top = 8.dp
                        )
                )
            }
        }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            ProductCounter(
                1,
                 orderCount,
                onProductIncreased = { orderCount++ },
                onProductDecreased = { if (orderCount > 0) orderCount-- },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPrice = basePrice * orderCount
            OrderButton(
                text = stringResource(R.string.add_to_cart_rp_d, totalPrice),
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                },
                modifier = Modifier.clickable {  }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DetailContentPreview() {
    MaterialTheme {
        DetailContent(
            "https://i.pinimg.com/564x/2e/0b/d2/2e0bd2052cf52f96d79fc61a0346197f.jpg",
            "Cozy Comfort Blanket",
            "Quilt",
            "Made with high quality yarn to provide extra comfort at night.",
            300000,
            1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}