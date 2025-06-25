package uz.shoxrux.data.mapper

import uz.shoxrux.data.locale.room.NoteEntity
import uz.shoxrux.domain.model.NoteItem

fun NoteEntity.toDomain(): NoteItem {
    return NoteItem(
        id = this.id,
        title = this.title,
        content = this.content
    )
}

fun NoteItem.toData(): NoteEntity {
    return NoteEntity(
        id = this.id,
        title = this.title,
        content = this.content
    )
}