import java.time.LocalDate
import java.time.LocalDateTime

data class Note(
   var title: String,
   var description: String,
   val time: LocalDate
)