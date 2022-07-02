package a;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.content.BroadcastReceiver;

public class d extends Service {

	BroadcastReceiver a;
	@Override
	public void onCreate() {
		super.onCreate();
		a = new e();
		((KeyguardManager) getSystemService(KEYGUARD_SERVICE)).newKeyguardLock("IN").disableKeyguard();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(this.a, intentFilter);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(a);
	}
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}
}
