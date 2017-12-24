package pl.bmideas.michal.bmnotifier

import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import pl.bmideas.michal.bmnotifier.Events.TestEvent

/**
 * Created by michal on 12/22/17.
 */
class MyJobService : JobService() {

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Log.d(TAG, "Performing long running task in scheduled job")
        // TODO(developer): add long running task here.
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }
    @Subscribe
    fun onTestMessageRecived(event : TestEvent){
        Log.w("MYSERVICE" , "Recived event " + event.title)
    }

    override  fun onCreate() {
        EventBus.getDefault().register(this);
    }

    companion object {

        private val TAG = "MyJobService"
    }

}