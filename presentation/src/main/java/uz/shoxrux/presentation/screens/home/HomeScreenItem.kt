package uz.shoxrux.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.shoxrux.presentation.R
import uz.shoxrux.presentation.ui.color.LocalAppColors

@Composable
fun NoteItem(
    title: String,
    content: String
) {

    val colors = LocalAppColors.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(colors.gray)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.nunito_medium)),
                    fontSize = 18.sp,
                    color = colors.titleText
                )
            )
            Text(
                text = content,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontSize = 16.sp,
                    color = colors.contentText
                )
            )
        }

    }

}