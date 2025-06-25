package uz.shoxrux.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.shoxrux.presentation.ui.color.LocalAppColors

@Composable
fun AppSmallButton(painter: Int, onClick: () -> Unit) {

    val colors = LocalAppColors.current

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .size(50.dp)
            .clickable {
                onClick.invoke()
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(colors.gray)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(painter),
                contentDescription = null
            )
        }
    }
}