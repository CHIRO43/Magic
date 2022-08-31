
import com.google.gson.annotations.SerializedName

data class DataClass(
    @SerializedName("profBulletIn")
    val profBulletIn: ProfBulletIn,
    @SerializedName("profDoNotDisturb")
    val profDoNotDisturb: Boolean,
    @SerializedName("profInfo")
    val profInfo: ProfInfo,
    @SerializedName("profLectureTimeTable")
    val profLectureTimeTable: ProfLectureTimeTable
)