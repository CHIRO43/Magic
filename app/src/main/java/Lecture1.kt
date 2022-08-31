
import com.google.gson.annotations.SerializedName

data class Lecture1(
    @SerializedName("day")
    val day: String="",
    @SerializedName("startTime")
    val startTime: String="",
    @SerializedName("endTime")
    val endTime: String="",
    @SerializedName("lectureClass")
    val lectureClass: String="",
    @SerializedName("lectureName")
    val lectureName: String="",
    @SerializedName("lectureRoom")
    val lectureRoom: String=""
)