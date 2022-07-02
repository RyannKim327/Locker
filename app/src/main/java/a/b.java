package a;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import android.view.View.OnLongClickListener;

public class b extends Service {

	RelativeLayout base;
	EditText et;
	WindowManager wm;
	WindowManager.LayoutParams params;
	SharedPreferences sp;
	TextView tv;
	int trials = 5,time = 15;
	Vibrator vib;
	@Override
	public void onCreate() {
		super.onCreate();
		setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
		base = new RelativeLayout(this);
		wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		params = new WindowManager.LayoutParams(
		WindowManager.LayoutParams.MATCH_PARENT,
		WindowManager.LayoutParams.MATCH_PARENT,
		(Build.VERSION.SDK_INT <= 25) ? WindowManager.LayoutParams.TYPE_PHONE :
		WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
		WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | 
		WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
		WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
		PixelFormat.TRANSLUCENT
		);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
		if(a(sp.getString(a.bg,"")) != null || new File(sp.getString(a.bg,"")).exists()){
			base.setBackgroundDrawable(new BitmapDrawable(a(sp.getString(a.bg,""))));
		}else{
			base.setBackgroundDrawable(getWallpaper());
		}
		base.setGravity(Gravity.CENTER);
		base.setPadding(10,0,10,0);
		
		LinearLayout l = new LinearLayout(this);
		et = new EditText(this);
	    tv = new TextView(this);
		ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(new float[]{10,10,10,10,10,10,10,10},null,null));
		ShapeDrawable sd2 = new ShapeDrawable(new RoundRectShape(new float[]{25,25,25,25,25,25,25,25},null,null));
		sd.getPaint().setColor(Color.argb(100,33,33,33));
		sd2.getPaint().setColor(Color.argb(75,175,175,175));
		l.setOrientation(LinearLayout.VERTICAL);
		l.setBackgroundDrawable(sd);
		l.setPadding(5,5,5,5);
		l.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		tv.setGravity(Gravity.CENTER);
		tv.setText("Enter your password");
		tv.setSingleLine();
		tv.setTextSize(19);
		tv.setTextColor(Color.WHITE);
		et.setSingleLine();
		et.setHint(sp.getString(a.hint_,""));
		et.setBackgroundDrawable(sd2);
		et.setGravity(Gravity.CENTER);
		et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		et.setTransformationMethod(new PasswordTransformationMethod());
		et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		et.addTextChangedListener(new TextWatcher(){
				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {}
				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {}
				@Override
				public void afterTextChanged(Editable p1) {
					if(et.getText().toString().equals(sp.getString(a.pash,""))){
						stopSelf();
						startService(new Intent(b.this,d.class));
					}
					if(et.getText().toString().length() == sp.getString(a.pash,"").length()){
						trials--;
						tv.setText("Enter your password");
					}
					if(trials == 0){
						et.setEnabled(false);
						et.setVisibility(View.GONE);
						a();
					}
				}
			});
		l.setOnLongClickListener(new OnLongClickListener(){
				@Override
				public boolean onLongClick(View p1) {
					//stopSelf();
					return false;
				}
			});
		l.addView(tv);
		l.addView(et);
		base.addView(l);
		wm.addView(base,params);
		if(sp.getString(a.pash,"").isEmpty()){
			stopSelf();
		}
	}
	void a(){
		tv.setText("Please wait for a moment: " + time + " sec");
		if(time <= 0){
			trials = 5;
			et.setEnabled(true);
			et.setText("");
			et.setVisibility(View.VISIBLE);
			tv.setText("Enter your password");
		}else{
			time--;
			vib.vibrate(1000);
	    	new Handler().postDelayed(new Runnable(){
	    			@Override
		    		public void run() {
				    	a();
	    			}
	    		},1000);
			}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		wm.removeView(base);
	}
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}
	Bitmap a(String _){
		try {
			return BitmapFactory.decodeStream(new FileInputStream(new File(_)));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
