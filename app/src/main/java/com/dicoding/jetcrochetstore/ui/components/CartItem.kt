package com.dicoding.jetcrochetstore.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.jetcrochetstore.R
import com.dicoding.jetcrochetstore.ui.theme.JetCrochetStoreTheme
import com.dicoding.jetcrochetstore.ui.theme.Shapes

@Composable
fun CartItem(
    crochetId: Long,
    image: String,
    title: String,
    totalPrice: Int,
    count: Int,
    onProductCountChange: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(R.string.price, totalPrice),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                modifier = modifier.padding(
                    top = 8.dp,
                )
            )
        }
        ProductCounter(
            orderId = crochetId,
            orderCount = count,
            onProductIncreased = { onProductCountChange(crochetId, count + 1) },
            onProductDecreased = { onProductCountChange(crochetId, count - 1) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    JetCrochetStoreTheme {
        CartItem(
            1,
            "https://i.pinimg.com/564x/2e/0b/d2/2e0bd2052cf52f96d79fc61a0346197f.jpg",
            "Cozy Comfort Blanket",
            300000,
            0,
            onProductCountChange = { crochetId, count -> },
        )
    }
}