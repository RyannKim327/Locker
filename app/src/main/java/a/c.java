package a;
import android.app.admin.DeviceAdminReceiver;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

public class c extends DeviceAdminReceiver {

	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
		Toast.makeText(context,"Done",1).show();
	}
	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Toast.makeText(context,"OFF",1).show();
	}
}
