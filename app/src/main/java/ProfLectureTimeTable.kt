
import com.google.gson.annotations.SerializedName

data class ProfLectureTimeTable(
    @SerializedName("days")
    var days: String,
    @SerializedName("lecture1")
    var lecture1: MutableList<Lecture1>
)

data class Lecture1(
    @SerializedName("day")
    var day: String,
    @SerializedName("startTime")
    var startTime: String,
    @SerializedName("endTime")
    var endTime: String,
    @SerializedName("lectureClass")
    var lectureClass: String,
    @SerializedName("lectureName")
    var lectureName: String,
    @SerializedName("lectureRoom")
    var lectureRoom: String
)
