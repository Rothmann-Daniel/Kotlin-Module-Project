data class Archive(
    var name: String,
    val notes: MutableList<Note> = mutableListOf()
)