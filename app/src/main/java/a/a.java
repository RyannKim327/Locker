package a;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.File;
import android.content.DialogInterface;
import java.util.Comparator;
import java.text.Collator;

public class a extends Activity {

	RelativeLayout base;
	LinearLayout a;
	EditText pass,hint, b,c;
	Button save;
	SharedPreferences sp;
	public static String pash = "mpop.revii.pref.password";
	public static String bg = "mpop.revii.pref.background";
	public static String hint_ = "mpop.revii.pref.hint";
	ComponentName com;
	DevicePolicyManager dam;
	ArrayAdapter<String> adapt;
	String dir = "/storage/emulated/0/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
		dam = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
		com = new ComponentName(this,c.class);
		base = new RelativeLayout(this);
		a = new LinearLayout(this);
		pass = new EditText(this);
		hint = new EditText(this);
		b = new EditText(this);
		c = new EditText(this);
		save = new Button(this);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(new float[]{10,10,10,10,10,10,10,10},null,null));
		ShapeDrawable sd2 = new ShapeDrawable(new RoundRectShape(new float[]{25,25,25,25,25,25,25,25},null,null));
		adapt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new File(dir).list());
		getWindow().setBackgroundDrawable(getWallpaper());
		base.setBackgroundColor(Color.TRANSPARENT);
		base.setPadding(5,0,5,0);
		base.setGravity(Gravity.CENTER);
		
		sd.getPaint().setColor(Color.DKGRAY);
		sd2.getPaint().setColor(Color.LTGRAY);
		
		a.setOrientation(LinearLayout.VERTICAL);
		a.setBackgroundDrawable(sd);
		a.setPadding(10,10,10,10);
		
		pass.setHint("Enter password:");
		pass.setSingleLine();
		pass.setGravity(Gravity.CENTER);
		pass.setBackgroundDrawable(sd2);
		pass.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		hint.setHint("Enter your hint:");
		hint.setSingleLine();
		hint.setGravity(Gravity.CENTER);
		hint.setBackgroundDrawable(sd2);
		hint.setText(sp.getString(hint_,""));
		hint.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		b.setVisibility((sp.getString(pash,"").isEmpty()) ? View.GONE : View.VISIBLE);
		b.setHint((sp.getString(pash,"").isEmpty()) ? "Re-enter your password" :"Enter old password to confirm:");
		b.setSingleLine();
		b.setGravity(Gravity.CENTER);
		b.setBackgroundDrawable(sd2);
		b.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		b.setTransformationMethod(new PasswordTransformationMethod());
		b.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		c.setHint("Enter image directory, hold to choose, blank as default:");
		c.setSingleLine();
		c.setGravity(Gravity.CENTER);
		c.setBackgroundDrawable(sd2);
		c.setText(sp.getString(bg,""));
		c.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		c.setOnLongClickListener(new OnLongClickListener(){
				@Override
				public boolean onLongClick(View p1) {
					A();
					return false;
				}
			});
		
		save.setText("Save all settings");
		save.setSingleLine();
		save.setGravity(Gravity.CENTER);
		save.setBackgroundDrawable(sd2);
		save.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		save.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {
					a();
				}
			});
		
		a.addView(hint);
		a.addView(v());
		a.addView(pass);
		a.addView(v());
		a.addView(b);
		a.addView(v());
		a.addView(c);
		a.addView(v());
		a.addView(save);
		base.addView(a);
		setContentView(base);
	}
	void a(){
		if(pass.getText().toString().isEmpty() && b.getText().toString().isEmpty()){
			startService(new Intent(this,b.class));
			finishAndRemoveTask();
		}else if(sp.getString(pash,"").equals(b.getText().toString())){
			sp.edit().putString(pash,pass.getText().toString()).commit();
			sp.edit().putString(bg,c.getText().toString()).commit();
			sp.edit().putString(hint_,hint.getText().toString()).commit();
			Toast.makeText(this,"Done",1).show();
			startService(new Intent(this,b.class));
			finishAndRemoveTask();
		}else{
			Toast.makeText(this,"Invalid settings",1).show();
		}
	}
	void A(){
		final AlertDialog.Builder dia = new AlertDialog.Builder(a.this);
		dia.setTitle("Select an image:");
		adapt.sort(new Comparator<String>(){
				@Override
				public int compare(String p1, String p2) {
					return Collator.getInstance().compare(p1,p2);
				}
			});
		dia.setAdapter(adapt, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2) {
					if(new File(dir + adapt.getItem(p2)).isFile()){
						c.setText(dir + adapt.getItem(p2));
					}else{
						dir += adapt.getItem(p2) + "/";
						adapt = new ArrayAdapter<String>(a.this,android.R.layout.simple_list_item_1,new File(dir).list());
						A();
					}
				}
			});
		dia.setPositiveButton("Done",null);
		dia.setNegativeButton("Up", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2) {
					String[] tempDir = dir.split("/");
					String temp = "";
					for(int i = 0; i < tempDir.length-1;i++){
						temp += tempDir[i] + "/";
					}
					if(dir.equals("/storage/emulated/0/")){
						dir = "/storage/emulated/0/";
						adapt = new ArrayAdapter<String>(a.this,android.R.layout.simple_list_item_1,new File(dir).list());
					}else{
						dir = temp;
						adapt = new ArrayAdapter<String>(a.this,android.R.layout.simple_list_item_1,new File(dir).list());
					}
					A();
				}
			});
		dia.setCancelable(false);
		dia.show();
	}
	View v(){
		View v = new View(this);
		//v.setPadding(0,5,0,5);
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,10));
		return v;
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(!dam.isAdminActive(com)){
			Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.putExtra("android.app.extra.DEVICE_ADMIN", com);
            intent.putExtra("android.app.extra.ADD_EXPLANATION", "Allow Admin Feature To Unlock all feature");
            startActivityForResult(intent, -3);
			finishAffinity();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
			case -3:
				try{
					Toast.makeText(a.this,"Done",1).show();
				}catch(Exception e){
					Toast.makeText(a.this,e.getMessage(),1).show();
				}
			break;
		}
	}
}
