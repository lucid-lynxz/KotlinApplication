package lynxz.org.kotlinapplication.avoidOnResult

import android.content.Intent

data class ActivityResultInfo(var requestCode: Int, var resultCode: Int, var data: Intent? = null)