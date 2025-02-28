interface InterfaceManager {

     fun createArchive()

     fun selectArchive(index: Int)

     fun createNote(archive: Archive)

     fun viewNote(note: Note)

     fun removeAllArchive()

     fun removeAllNotes(archiveIndex: Int)

     fun deletedArchive(index: Int)

     fun deletedNote(archiveIndex: Int, noteIndex: Int)

     fun editNoteTitle(archive: Archive, noteIndex: Int)

     fun editNoteDescription(archive: Archive, noteIndex: Int)

     fun editArchiveName(archiveIndex: Int)

}