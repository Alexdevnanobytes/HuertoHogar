package com.example.huertohogar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.huertohogar.model.Product

@Composable
fun ProductCard(
    product: Product,
    onAdd: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var qty by remember { mutableStateOf(1) }

    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)) // ✅ bordes suaves
                    .padding(4.dp),
                contentScale = ContentScale.Fit // ✅ no recorta la imagen
            )


            Text(product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text("$ ${product.price}", style = MaterialTheme.typography.bodyMedium)

            QuantitySelector(value = qty, onChange = { qty = it })

            Button(
                onClick = { onAdd(qty) },
                enabled = qty > 0,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Agregar") }
        }
    }
}
