import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlightRecord(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val flightNumber: String,
    val date: String,
    val origin: String,
    val destination: String,
    val scheduledTimeMinutes: Int,
    val delayMinutes: Int
)