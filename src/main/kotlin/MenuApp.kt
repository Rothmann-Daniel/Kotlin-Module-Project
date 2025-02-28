import java.time.LocalDate
import java.util.Scanner

class MenuApp : InterfaceManager {

    private val archives: MutableList<Archive> = mutableListOf()
    private val scanner = Scanner(System.`in`)

    override fun createArchive() {
        println("Введите имя архива:")
        val name = readlnOrNull()?.trim()
        if (name.isNullOrEmpty()) {
            println("Имя архива не может быть пустым.")
        } else {
            archives.add(Archive(name))
            println("Архив '$name' создан.")
        }
    }

    override fun selectArchive(index: Int) {
        val selectedArchive = archives[index]
        while (true) {
            println("Выбор заметки в архиве '${selectedArchive.name}':")
            println("0. Создать заметку")
            for (i in selectedArchive.notes.indices) {
                println("${i + 1}. ${selectedArchive.notes[i].title}")
            }
            println("${selectedArchive.notes.size + 1}. Удалить все заметки")
            println("${selectedArchive.notes.size + 2}. Удалить архив")
            println("${selectedArchive.notes.size + 3}. Изменить имя архива")
            println("${selectedArchive.notes.size + 4}. Назад")

            val choice = readInput()
            when (choice) {
                0 -> createNote(selectedArchive)
                in 1..selectedArchive.notes.size -> {
                    println("Выберите действие для заметки '${selectedArchive.notes[choice - 1].title}':")
                    println("1. Просмотреть заметку")
                    println("2. Удалить заметку")
                    println("3. Изменить заголовок заметки")
                    println("4. Изменить описание заметки")
                    println("5. Назад")
                    when (readInput()) {
                        1 -> viewNote(selectedArchive.notes[choice - 1])
                        2 -> deletedNote(index, choice - 1)
                        3 -> editNoteTitle(selectedArchive, choice - 1)
                        4 -> editNoteDescription(selectedArchive, choice - 1)
                        5-> return
                        else -> println("Неверный ввод, попробуйте снова.")
                    }
                }
                selectedArchive.notes.size + 1 -> removeAllNotes(index)
                selectedArchive.notes.size + 2 -> {
                    deletedArchive(index)
                    return
                }
                selectedArchive.notes.size + 3 -> editArchiveName(index)
                selectedArchive.notes.size + 4 -> return
                else -> println("Неверный ввод, попробуйте снова.")
            }
        }
    }

    override fun createNote(archive: Archive) {
        println("Введите заголовок для заметки:")
        val title = readlnOrNull()?.trim()
        if (title.isNullOrEmpty()) {
            println("Ошибка: Заголовок не может быть пустым.")
            return
        }

        println("Введите описание:")
        val description = readlnOrNull()?.trim()
        if (description.isNullOrBlank()) {
            println("Ошибка: Описание не может быть пустым или состоять только из пробелов.")
            return
        }

        val time = LocalDate.now()
        archive.notes.add(Note(title, description, time))
        println("Заметка \"$title\" успешно создана.")
    }

    override fun viewNote(note: Note) {
        println("Заметка: ${note.title}")
        println("Описание: ${note.description}")
        println("Дата создания заметки: ${note.time}")
        println("Нажмите Enter, чтобы вернуться назад.")
        readlnOrNull()
    }

    override fun removeAllArchive() {
        archives.clear()
        println("Все архивы удалены.")
        println()
    }

    override fun removeAllNotes(archiveIndex: Int) {
        if (archiveIndex in archives.indices) {
            val archive = archives[archiveIndex]
            archive.notes.clear()
            println("Все заметки в архиве '${archive.name}' удалены.")
        } else {
            println("Ошибка: Архив с индексом $archiveIndex не найден.")
        }
    }

    override fun deletedArchive(index: Int) {
        if (index in archives.indices) {
            val removedArchive = archives.removeAt(index)
            println("Архив '${removedArchive.name}' удален.")
        } else {
            println("Ошибка: Архив с индексом $index не найден.")
        }
    }

    override fun deletedNote(archiveIndex: Int, noteIndex: Int) {
        if (archiveIndex in archives.indices) {
            val archive = archives[archiveIndex]
            if (noteIndex in archive.notes.indices) {
                val note = archive.notes.removeAt(noteIndex)
                println("Заметка '${note.title}' удалена из архива '${archive.name}'.")
            } else {
                println("Ошибка: Заметка с индексом $noteIndex не найдена.")
            }
        } else {
            println("Ошибка: Архив с индексом $archiveIndex не найден.")
        }
    }

    override fun editNoteTitle(archive: Archive, noteIndex: Int) {
        if (noteIndex in archive.notes.indices) {
            println("Введите новый заголовок для заметки:")
            val newTitle = readlnOrNull()?.trim()
            if (newTitle.isNullOrEmpty()) {
                println("Ошибка: Заголовок не может быть пустым.")
            } else {
                archive.notes[noteIndex].title = newTitle
                println("Заголовок заметки изменен на '$newTitle'.")
            }
        } else {
            println("Ошибка: Заметка с индексом $noteIndex не найдена.")
        }
    }

    override fun editNoteDescription(archive: Archive, noteIndex: Int) {
        if (noteIndex in archive.notes.indices) {
            println("Введите новое описание для заметки:")
            val newDescription = readlnOrNull()?.trim()
            if (newDescription.isNullOrBlank()) {
                println("Ошибка: Описание не может быть пустым или состоять только из пробелов.")
            } else {
                archive.notes[noteIndex].description = newDescription
                println("Описание заметки изменено.")
            }
        } else {
            println("Ошибка: Заметка с индексом $noteIndex не найдена.")
        }
    }

    override fun editArchiveName(archiveIndex: Int) {
        if (archiveIndex in archives.indices) {
            println("Введите новое имя для архива:")
            val newName = readlnOrNull()?.trim()
            if (newName.isNullOrEmpty()) {
                println("Ошибка: Имя архива не может быть пустым.")
            } else {
                archives[archiveIndex].name = newName
                println("Имя архива изменено на '$newName'.")
            }
        } else {
            println("Ошибка: Архив с индексом $archiveIndex не найден.")
        }
    }

    private fun readInput(): Int {
        return try {
            scanner.nextLine().toInt()
        } catch (e: NumberFormatException) {
            -1
        }
    }

    fun start() {
        while (true) {
            println("Список архивов:")
            println("0. Создать архив")
            for (i in archives.indices) {
                println("${i + 1}. ${archives[i].name}")
            }
            println("${archives.size + 1}. Удалить все архивы")
            println("${archives.size + 2}. Выход")

            val choice = readInput()
            when (choice) {
                0 -> createArchive()
                in 1..archives.size -> {
                    println("Выберите действие для архива '${archives[choice - 1].name}':")
                    println("1. Выбрать архив")
                    println("2. Удалить архив")
                    println("3. Назад")
                    when (readInput()) {
                        1 -> selectArchive(choice - 1)
                        2 -> deletedArchive(choice - 1)
                        3 -> continue
                        else -> println("Неверный ввод, попробуйте снова.")
                    }
                }
                archives.size + 1 -> removeAllArchive()
                archives.size + 2 -> {
                    println("Спасибо за использование приложения! До свидания!")
                    return
                }else -> println("Неверный ввод, попробуйте снова.")
            }
        }
    }
}