
import com.google.gson.annotations.SerializedName

data class ProfInfo(
    @SerializedName("profEmail")
    var profEmail: String ,
    @SerializedName("profMajor")
    val profMajor: String= "",
    @SerializedName("profName")
    val profName: String= "",
    @SerializedName("profPosition")
    val profPosition: String= "",
    @SerializedName("profTelNum")
    val profTelNum: String= ""
)