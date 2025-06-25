package uz.shoxrux.presentation.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import uz.shoxrux.presentation.R
import uz.shoxrux.presentation.ui.color.LocalAppColors

@Composable
fun NoteItem(
    title: String,
    content: String,
    isSelected: Boolean,
    isChecking: Boolean,
    onItemClick: () -> Unit,
    onCheckChanged: (Boolean) -> Unit,
    onLongPressed: () -> Unit
) {

    val colors = LocalAppColors.current

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onItemClick.invoke()
                    },
                    onLongPress = {
                        onLongPressed.invoke()
                    }
                )
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(colors.gray)
    ) {

        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            Column {

                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = title,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nunito_medium)),
                        fontSize = 18.sp,
                        color = colors.titleText
                    )
                )
                Text(
                    text = content,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontSize = 16.sp,
                        color = colors.contentText
                    )
                )
            }

            if (isChecking) {

                Spacer(Modifier.weight(1f))

                Checkbox(
                    colors = CheckboxDefaults.colors(colors.primary),
                    checked = isSelected,
                    onCheckedChange = {
                        onCheckChanged.invoke(isSelected)
                    }
                )
            }

        }
    }
}

@Composable
fun AnimatedSearchBar(
    searchText: String,
    onTextChange: (String) -> Unit,
    isSearching: Boolean,
    onToggleSearch: () -> Unit
) {
    val colors = LocalAppColors.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearching) {
        if (isSearching) {
            delay(200)
            focusRequester.requestFocus()
        }
    }

    AnimatedContent(
        targetState = isSearching,
        transitionSpec = {
            fadeIn(tween(300)) togetherWith fadeOut(tween(200))
        },
        label = "SearchBarTransform"
    ) { searching ->
        if (searching) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(colors.gray)
            ) {
                TextField(
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colors.transparent,
                        unfocusedContainerColor = colors.transparent,
                        focusedIndicatorColor = colors.transparent,
                        unfocusedIndicatorColor = colors.transparent,
                        cursorColor = colors.primary
                    ),
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nunito_medium)),
                        fontSize = 14.sp,
                        color = colors.contentText
                    ),
                    value = searchText,
                    onValueChange = onTextChange,
                    placeholder = {
                        Text(
                            text = "Search...",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.nunito_medium)),
                                fontSize = 14.sp,
                                color = colors.hint
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = onToggleSearch) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                )
            }
        } else {
            Row {
                Spacer(Modifier.weight(1f))

                Card(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onToggleSearch()
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(colors.gray)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Icon(
                            modifier = Modifier.align(Alignment.Center),
                            contentDescription = null,
                            painter = painterResource(R.drawable.ic_search),
                            tint = Color.White
                        )
                    }
                }
            }

        }
    }
}