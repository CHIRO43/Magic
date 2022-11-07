
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ProfLectureTimeTable(
    @SerializedName("days")
    var days: String? ="",
    @SerializedName("lecture1")
    var lecture1: MutableList<Lecture1> ?= ArrayList()
)

data class Lecture1(
    @SerializedName("day")
    var day: String? = null,
    @SerializedName("startTime")
    var startTime: String? = null,
    @SerializedName("endTime")
    var endTime: String?= null,
    @SerializedName("lectureClass")
    var lectureClass: String?= null,
    @SerializedName("lectureName")
    var lectureName: String?= null,
    @SerializedName("lectureRoom")
    var lectureRoom: String? = null
)
