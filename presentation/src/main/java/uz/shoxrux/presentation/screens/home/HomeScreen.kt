package uz.shoxrux.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uz.shoxrux.core.utils.NavRoutes
import uz.shoxrux.presentation.R
import uz.shoxrux.presentation.ui.color.LocalAppColors

@Composable
fun HomeScreen(

    viewModel: HomeViewModel,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val deletingIndicatorIsActive = remember { mutableStateOf(false) }

    val selectedNotes = viewModel.selectedNotes.collectAsState().value

    val notes = viewModel.notes.collectAsState().value

    val allItemsChecked = selectedNotes.size == notes.size && notes.isNotEmpty()

    val isSearching = viewModel.isSearching.collectAsState().value
    val searchText = viewModel.searchText.collectAsState().value

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val noteSaved = savedStateHandle?.get<Boolean>("note_saved") == true

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(noteSaved) {
        if (noteSaved) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Note saved")
            }
            savedStateHandle?.set("note_saved", false)
        }
    }

    val colors = LocalAppColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 16.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(Modifier.height(20.dp))

            if (deletingIndicatorIsActive.value) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            deletingIndicatorIsActive.value = false
                            viewModel.clearSelected()
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            null
                        )

                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = selectedNotes.size.toString(),
                        style = TextStyle(
                            color = colors.titleText,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_medium))
                        )
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "Select All",
                        style = TextStyle(
                            color = colors.contentText,
                            fontFamily = FontFamily(Font(R.font.nunito_medium)),
                            fontSize = 18.sp
                        )
                    )

                    Checkbox(
                        colors = CheckboxDefaults.colors(colors.primary),
                        checked = allItemsChecked,
                        onCheckedChange = {
                            viewModel.selectAll()
                        }
                    )

                    IconButton(
                        onClick = {
                            if (selectedNotes.isNotEmpty()) {
                                val ids = mutableListOf(0)
                                selectedNotes.forEachIndexed { _, noteItem ->
                                    ids.add(noteItem.id)
                                }
                                Toast.makeText(navController.context, "Deleted", Toast.LENGTH_SHORT)
                                    .show()
                                deletingIndicatorIsActive.value = false
                                viewModel.deleteNotesById(ids)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            tint = Color.Red,
                            contentDescription = null
                        )
                    }

                }

            } else {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    if (!isSearching) {

                        Text(
                            text = "Notes",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                                fontSize = 22.sp,
                                color = colors.titleText
                            )
                        )
                    }
                    AnimatedSearchBar(
                        searchText = searchText,
                        onTextChange = {
                            viewModel.onNewText(it)
                        },
                        onToggleSearch = {
                            viewModel.toggleSearchState()
                        },
                        isSearching = isSearching
                    )
                }
            }

            if (notes.isNotEmpty()) {
                LazyColumn(Modifier.padding(top = 20.dp)) {
                    items(notes.size) { itIndex ->
                        val note = notes[itIndex]
                        val isSelected = selectedNotes.any { it.id == note.id }

                        NoteItem(
                            title = note.title,
                            content = note.content,
                            isChecking = deletingIndicatorIsActive.value,
                            isSelected = isSelected,
                            onItemClick = {
                                if (!deletingIndicatorIsActive.value) {
                                    navController.navigate(NavRoutes.DETAIL_SCREEN + "/${note.id}")
                                } else {
                                    viewModel.toggleNoteSelection(note)
                                }
                            },
                            onLongPressed = {
                                deletingIndicatorIsActive.value = true
                                viewModel.toggleNoteSelection(note)
                            },
                            onCheckChanged = {
                                viewModel.toggleNoteSelection(note)
                            }
                        )

                        Spacer(Modifier.height(20.dp))
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 50.dp)
                                .size(200.dp),
                            painter = painterResource(R.drawable.empty_note_image),
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "No Notes",
                            style = TextStyle(
                                color = colors.contentText,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.nunito_medium))
                            )
                        )

                        Spacer(Modifier.height(40.dp))
                    }
                }
            }
        }

        if (!deletingIndicatorIsActive.value) {

            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .size(50.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    navController.navigate(NavRoutes.DETAIL_SCREEN + "/-1")
                },
                shape = RoundedCornerShape(8.dp),
                containerColor = colors.gray
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.White
                )

            }
        }
    }
}