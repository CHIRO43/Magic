
import com.google.gson.annotations.SerializedName

data class ProfLectureTimeTable(
    @SerializedName("day")
    val day: String="",
    @SerializedName("lecture1")
    val lecture1: MutableList<Lecture1> = mutableListOf(Lecture1())
)
