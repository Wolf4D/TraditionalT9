package org.nyanya.android.traditionalt9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu1 extends Activity {
	public static TraditionalT9 mTraditionalT9;
	int lang;
	String origword;
	public void AddWord() {
		Intent i = getIntent();
		origword = i.getStringExtra("org.nyanya.android.traditionalt9.word");

		lang = i.getIntExtra("org.nyanya.android.traditionalt9.lang", -1);
		if (lang == -1) {
			Log.e("AddWordAct.onCreate", "lang is invalid. How?");
		}
				Intent awintent = new Intent(Menu1.this, AddWordAct.class);
				awintent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				awintent.putExtra("org.nyanya.android.traditionalt9.word", origword.toString());
				awintent.putExtra("org.nyanya.android.traditionalt9.lang", lang);
				finish();
				startActivity(awintent);
	}
	
	protected void onCreate(Bundle savedInstanceState) {
	    
	    WindowManager.LayoutParams params = getWindow().getAttributes();  
	     
	    params.height = 10;  
	    params.width = 50;  
	    params.y = 1000;  

	    Menu1.this.getWindow().setAttributes(params); 

	    requestWindowFeature(Window. FEATURE_NO_TITLE);
	    super.onCreate(savedInstanceState);
	    
		View convertView = getLayoutInflater().inflate(R.layout.menuview, null);
		final String[] menu1 = getResources().getStringArray(R.array.menu1);
        
        ListView lv = (ListView) convertView.findViewById(R.id.menuview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.my_list_item,menu1);
     
        lv.setOnItemClickListener(new OnItemClickListener()
        {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
						finish();
                    	AddWord();
                        break;
                    case 1:
						finish();
                        startActivity(new Intent(Menu1.this, TraditionalT9Settings.class));
                        break;
                }
           }
        });
        
        lv.setAdapter(adapter);
        setContentView(convertView);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				finish();
				return true;
		}
		return false;
		}

}



