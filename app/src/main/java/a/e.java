package a;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;

public class e extends BroadcastReceiver {

	boolean b = true;
	@Override
	public void onReceive(Context p1, Intent p2) {
		if(p2.getAction().equals(Intent.ACTION_SCREEN_OFF)){
			b = false;
			Intent a = new Intent(p1,b.class);
			a.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
			p1.startService(a);
		}else if(p2.getAction().equals(Intent.ACTION_SCREEN_ON)){
			b = true;
			new Intent(p1,b.class).addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
		}else if(p2.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			Intent a = new Intent(p1,b.class);
			a.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
			p1.startService(a);
		}
	}
}
