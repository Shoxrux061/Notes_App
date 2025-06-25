package uz.shoxrux.presentation.screens.detail

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uz.shoxrux.domain.model.NoteItem
import uz.shoxrux.presentation.R
import uz.shoxrux.presentation.ui.color.LocalAppColors
import uz.shoxrux.presentation.ui.components.AppSmallButton

@Composable
fun DetailsScreen(navController: NavHostController, viewModel: DetailViewModel, noteId: Int = -1) {

    BackHandler {
        navController.popBackStack()
        viewModel.clearState()
    }

    LaunchedEffect(Unit) {
        if (noteId != -1) {
            viewModel.setArgument(noteId)
        }
    }

    val titleText = viewModel.titleText.collectAsState()
    val contentText = viewModel.contentText.collectAsState()

    val isSuccess = viewModel.isSuccess.collectAsState().value
    val error = viewModel.error.collectAsState().value
    val colors = LocalAppColors.current

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("note_saved", true)
            navController.popBackStack()
            viewModel.clearState()
        }
    }
    LaunchedEffect(error) {

        Log.d("TAGError", "DetailsScreen:$error")

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {

            AppSmallButton(
                painter = R.drawable.ic_back,
                onClick = {
                    navController.popBackStack()
                    viewModel.clearState()
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            AppSmallButton(
                painter = R.drawable.ic_save,
                onClick = {

                    if (contentText.value.isNotBlank()) {
                        val note = NoteItem(
                            title = titleText.value.ifBlank { "New Note" },
                            content = contentText.value
                        )
                        if (noteId == -1) {
                            viewModel.addNote(note)
                        } else {
                            Log.d("TAGEdit", "DetailsScreen: edit button")
                            viewModel.editNote(note.copy(id = noteId))
                        }
                    }
                }
            )
        }

        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.transparent,
                unfocusedContainerColor = colors.transparent,
                focusedIndicatorColor = colors.transparent,
                unfocusedIndicatorColor = colors.transparent,
                cursorColor = colors.primary
            ),
            placeholder = {
                Text(
                    text = "Title",
                    color = colors.hint,
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    fontSize = 24.sp
                )
            },
            textStyle = TextStyle(
                color = colors.titleText,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontSize = 24.sp
            ),
            value = titleText.value,
            onValueChange = {
                viewModel.onTitleNewText(it)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.transparent,
                unfocusedContainerColor = colors.transparent,
                focusedIndicatorColor = colors.transparent,
                unfocusedIndicatorColor = colors.transparent,
                cursorColor = colors.primary
            ),
            placeholder = {
                Text(
                    text = "Type something...",
                    color = colors.hint,
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(
                color = colors.titleText,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontSize = 16.sp
            ),
            value = contentText.value,
            onValueChange = {
                viewModel.onContentNewText(it)
            }
        )

    }
}