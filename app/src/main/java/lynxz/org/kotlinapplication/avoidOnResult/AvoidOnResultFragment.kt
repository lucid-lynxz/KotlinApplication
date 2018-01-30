package lynxz.org.kotlinapplication.avoidOnResult

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Created by lynxz on 02/01/2018.
 */
class AvoidOnResultFragment : Fragment() {
    private val mSubjects = hashMapOf<Int, PublishSubject<ActivityResultInfo>>()
    private val mCallbacks = hashMapOf<Int, AvoidOnResult.Callback?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startForResult(intent: Intent, requestCode: Int, callback: AvoidOnResult.Callback? = null): Observable<ActivityResultInfo> =
            PublishSubject.create<ActivityResultInfo>().let {
                mCallbacks.put(requestCode, callback)
                mSubjects.put(requestCode, it)
                it.doOnSubscribe {
                    startActivityForResult(intent, requestCode)
                }
            }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSubjects.remove(requestCode)?.let {
            it.onNext(ActivityResultInfo(requestCode, resultCode, data))
            it.onComplete()
        }

        mCallbacks.remove(requestCode)?.let {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }
}