import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tf.travelfinances.Database.MainDB
import com.tf.travelfinances.Database.Travel
import com.tf.travelfinances.data.TravelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NewTravelViewModel(application: Application) : AndroidViewModel(application) {
    fun addTravel(name: String, departure: String, arrival: String, splitExpenses: Boolean, peopleForSplit: Int, humanNames: MutableList<String>, database: MainDB) {
        val travel = Travel(
            name = name,
            departure = departure,
            arrival = arrival,
            splitExpenses = splitExpenses,
            peopleForSplit = peopleForSplit,
            humanNames = humanNames
        )
        Thread {
            database.getDao().addTravel(travel)
        }.start()
    }

    fun isValidDate(arrival: String, departure: String): Int {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        format.isLenient = false

        try {
            val arrivalDate: Date = format.parse(arrival) ?: return -1
            val departureDate: Date = format.parse(departure) ?: return 0

            //отъезд позже прибытия
            if (arrivalDate.before(departureDate)) {
                return -1
            }

            val calendar = Calendar.getInstance()
            calendar.time = departureDate
            val departureYear = calendar.get(Calendar.YEAR)
            //поездки прошлого года отсекаются
            if (departureYear < currentYear) {
                return 0
            }

            return 1
        } catch (e: Exception) {
            return -2
        }
    }

}
