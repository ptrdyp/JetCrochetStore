package com.dicoding.jetcrochetstore.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun CrochetItem (
    image: String,
    title: String,
    category: String,
    price: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(16.dp)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .padding(8.dp)
                .width(120.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = modifier
                .padding(8.dp)
        ) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = modifier.padding(
                    bottom = 8.dp
                )
            )
            Text(
                text = category,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                modifier = modifier.padding(
                    bottom = 8.dp,
                )
            )
            Text(
                text = description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.price, price),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = modifier.padding(
                    top = 16.dp,
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CrochetItemPreview() {
    MaterialTheme {
        CrochetItem(
            "https://i.pinimg.com/564x/2e/0b/d2/2e0bd2052cf52f96d79fc61a0346197f.jpg",
            "Cozy Comfort Blanket",
            "Quilt",
            300000,
            "Made with high quality yarn to provide extra comfort at night. blablabalblablablabalblablablabalblablablabalbla",
        )
    }
}