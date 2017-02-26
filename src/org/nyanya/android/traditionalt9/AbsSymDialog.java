package org.nyanya.android.traditionalt9;

import android.app.Dialog;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public abstract class AbsSymDialog extends Dialog implements
		View.OnClickListener {

	private static String insSmiley;
	private static String insSymbol;
	private String insPage;
	private KeyboardView.OnKeyboardActionListener parent;
	private View mainview;
	private int pagenum = 1;
	private int pageoffset = (pagenum - 1) * 10;

	private int MAX_PAGE;
	private String title;
	private boolean started;
	public static int ACCESSIBILITY_LIVE_REGION_POLITE;
	private static final int[] buttons = {
		R.id.text_keyone,   R.id.text_keytwo,
		R.id.text_keythree, R.id.text_keyfour,  R.id.text_keyfive,
		R.id.text_keysix,   R.id.text_keyseven, R.id.text_keyeight,
		R.id.text_keynine,  R.id.text_keyzero
	};
	

	private static final Integer[] smileysImg = {
		R.drawable.smile, R.drawable.winking, R.drawable.laughing, R.drawable.tongue, 
		R.drawable.straight_face, R.drawable.sad, R.drawable.not_talking, R.drawable.nerd, R.drawable.crying, R.drawable.crying2, 
		R.drawable.love, R.drawable.heart, R.drawable.broken_heart, R.drawable.flower, R.drawable.kiss, R.drawable.big_grin, R.drawable.angry, R.drawable.confused, R.drawable.devil, R.drawable.dizzy, 
		R.drawable.dont_tell_anyone, R.drawable.bomb, R.drawable.dead, R.drawable.drooling, R.drawable.music, R.drawable.party, R.drawable.sacred, R.drawable.scared, R.drawable.silly, R.drawable.smoking, 
		R.drawable.smug, R.drawable.thinking, R.drawable.yawn, R.drawable.cat, R.drawable.dog, R.drawable.pig, R.drawable.clown, R.drawable.cool, R.drawable.vomit, R.drawable.hug, 
		R.drawable.sick, R.drawable.loser, R.drawable.sleeping, R.drawable.wave, R.drawable.whew, R.drawable.stars, R.drawable.drinks, R.drawable.balloon, R.drawable.ligthbulb, R.drawable.zombie, };

	private static final Integer[] numImg = {
		R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4, 
		R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8, R.drawable.num9, R.drawable.num0,  };

	
	private static final int[] buttons2 = {
		R.id.text_keystar,
		R.id.text_keypound
	};
	
	public AbsSymDialog(Context c, View mv) {
		super(c);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		parent = (KeyboardView.OnKeyboardActionListener) c;
		mainview = mv;
		started = true;
		setContentView(mv);

		View button;
		for (int butt : buttons) {
			button = mv.findViewById(butt);
			button.setOnClickListener(this);
		}
		for (int butt : buttons2) {
			button = mv.findViewById(butt);
			button.setOnClickListener(this);
			button.setBackgroundResource(R.drawable.button_gradient_orange);
			
		}
		MAX_PAGE = getMaxPage();
		title = getTitleText();
		insSmiley = mv.getResources().getString(R.string.ins_smiley);
		insSymbol = mv.getResources().getString(R.string.ins_symbol);
		insPage = mv.getResources().getString(R.string.ins_page);

	}

	public String getSmileyCD(int index) {
		String[] smileyCD = mainview.getResources().getStringArray(R.array.smileyCD);
		return smileyCD[index];
	}
	

	protected Integer getSmileyImg(int index) {
		return smileysImg[index];
	}

	protected Integer getNumImg(int index) {
		return numImg[index];
	}
	
	public String getSymbolCD(int index) {	
		String[] symbolCD = mainview.getResources().getStringArray(R.array.symbolCD);
	
		return symbolCD[index];
	}
	@Override
	public void onClick(View v) {
		// Log.d("SymbolPopup - onClick", "click happen: " + v);
		switch (v.getId()) {
		case R.id.text_keyone:
			sendChar(pageoffset);
			break;
		case R.id.text_keytwo:
			sendChar(pageoffset + 1);
			break;
		case R.id.text_keythree:
			sendChar(pageoffset + 2);
			break;
		case R.id.text_keyfour:
			sendChar(pageoffset + 3);
			break;
		case R.id.text_keyfive:
			sendChar(pageoffset + 4);
			break;
		case R.id.text_keysix:
			sendChar(pageoffset + 5);
			break;
		case R.id.text_keyseven:
			sendChar(pageoffset + 6);
			break;
		case R.id.text_keyeight:
			sendChar(pageoffset + 7);
			break;
		case R.id.text_keynine:
			sendChar(pageoffset + 8);
			break;
		case R.id.text_keyzero:
			sendChar(pageoffset + 9);
			break;

		case R.id.text_keypound:
			pageChange(1);
			break;
		case R.id.text_keystar:
			pageChange(-1);
			break;
		}
	}

	protected abstract String getSymbol(int index);
	protected abstract String getTitleText();
	protected abstract int getSymbolSize();
	protected abstract int getMaxPage();

	private void sendChar(int index) {
		// Log.d("SymbolDialog - sendChar", "Sending index: " + index);

		if (index < getSymbolSize()) {
			parent.onText(getSymbol(index));
			// then close
			pagenum = 1;
			pageoffset = (pagenum - 1) * 10;
			this.dismiss();
		}
	}

	private void pageChange(int amt) {
		pagenum = pagenum + amt;
		if (pagenum > MAX_PAGE) {
			pagenum = 1;
		} else if (pagenum < 1) {
			pagenum = MAX_PAGE;
		}
		pageoffset = (pagenum - 1) * 10;
		updateButtons();
	}
	
	private void updateButtons() {
		// set page number text
		if (title == "Smiley") {
				title = insSmiley;
		} else if (title == "Symbol") {
			title = insSymbol;
		}
		setTitle(title +"\t" + insPage + pagenum + "/" + MAX_PAGE);
		// update button labels
		int symbx = pageoffset;
		int stop = symbx + 9;
		int nomore = stop;
		int symsize = getSymbolSize();
		if (nomore >= symsize - 1) {
			nomore = symsize - 1;
		}
		for (int buttx = 0; symbx <= stop; symbx++) {
			// Log.d("SymbolDialog - updateButtons", "buttx: " + buttx +
			// " symbx: " + symbx);
			if (symbx > nomore) {
				Button buttond = (Button) mainview.findViewById(buttons[buttx]);
				buttond.setText("");
				mainview.findViewById(buttons[buttx])
				.setContentDescription("");
			} else {
				 Button buttond = (Button) mainview.findViewById(buttons[buttx]);
				TextView title_dialog = (TextView)  mainview.findViewById(R.id.title_symbol_dialog);
				TextView pagenum_dialog = (TextView)  mainview.findViewById(R.id.title_symbol_pagenum);

				pagenum_dialog.setText(pagenum + "/" + MAX_PAGE);
				title_dialog.setText(title);
				pagenum_dialog.setFocusable(true);
				if (title == insSmiley) {
					mainview.findViewById(buttons[buttx])
					.setContentDescription(String.valueOf(getSmileyCD(symbx)));
					buttond.setBackgroundResource(R.drawable.button_gradient);
					buttond.setCompoundDrawablesWithIntrinsicBounds(Integer.valueOf(getNumImg(buttx)), 0, Integer.valueOf(getSmileyImg(symbx)), 0);
				} else if (title == insSymbol) {
					mainview.findViewById(buttons[buttx])
					.setContentDescription(String.valueOf(getSymbolCD(symbx)));
					buttond.setCompoundDrawablesWithIntrinsicBounds(Integer.valueOf(getNumImg(buttx)), 0, 0, 0);
					buttond.setText(String.valueOf(getSymbol(symbx)));
					buttond.setBackgroundResource(R.drawable.button_gradient_blue);
					
				}
			}
			buttx++;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getRepeatCount() != 0) {
			return true;
		}
		if (started) {
			started = false;
		}
		// TODO: remove emulator special keys
		switch (keyCode) {
		case 75:
			keyCode = KeyEvent.KEYCODE_POUND;
			break;
		case 74:
			keyCode = KeyEvent.KEYCODE_STAR;
			break;
		}
		// Log.d("AbsSymDialog.onKeyDown", "bootan pres: " + keyCode);
		switch (keyCode) {

		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_1:
		case KeyEvent.KEYCODE_2:
		case KeyEvent.KEYCODE_3:
		case KeyEvent.KEYCODE_4:
		case KeyEvent.KEYCODE_5:
		case KeyEvent.KEYCODE_6:
		case KeyEvent.KEYCODE_7:
		case KeyEvent.KEYCODE_8:
		case KeyEvent.KEYCODE_9:
		case KeyEvent.KEYCODE_POUND:
		case KeyEvent.KEYCODE_STAR:
			event.startTracking();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// Log.d("AbsSymDialog.onKeyUp", "Key: " + keyCode);
		if (started) {
			started = false;
			return true;
		}
		// TODO: remove emulator special keys
		switch (keyCode) {
		case 75:
			keyCode = KeyEvent.KEYCODE_POUND;
			break;
		case 74:
			keyCode = KeyEvent.KEYCODE_STAR;
			break;
		}
		switch (keyCode) {
		// pass-through
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_1:
		case KeyEvent.KEYCODE_2:
		case KeyEvent.KEYCODE_3:
		case KeyEvent.KEYCODE_4:
		case KeyEvent.KEYCODE_5:
		case KeyEvent.KEYCODE_6:
		case KeyEvent.KEYCODE_7:
		case KeyEvent.KEYCODE_8:
		case KeyEvent.KEYCODE_9:
		case KeyEvent.KEYCODE_POUND:
		case KeyEvent.KEYCODE_STAR:
			onKey(keyCode);
			return true;
		default:
			// KeyCharacterMap.load(KeyCharacterMap.BUILT_IN_KEYBOARD).getNumber(keyCode)
			// Log.w("onKeyUp", "Unhandled Key: " + keyCode + "(" +
			// event.toString() + ")");
		}
		return super.onKeyUp(keyCode, event);
	}

	private void onKey(int keyCode) {
		// Log.d("OnKey", "pri: " + keyCode);
		// Log.d("onKey", "START Cm: " + mCapsMode);
		// HANDLE SPECIAL KEYS
		switch (keyCode) {
		case KeyEvent.KEYCODE_1:
			sendChar(pageoffset);
			break;
		case KeyEvent.KEYCODE_2:
			sendChar(pageoffset + 1);
			break;
		case KeyEvent.KEYCODE_3:
			sendChar(pageoffset + 2);
			break;
		case KeyEvent.KEYCODE_4:
			sendChar(pageoffset + 3);
			break;
		case KeyEvent.KEYCODE_5:
			sendChar(pageoffset + 4);
			break;
		case KeyEvent.KEYCODE_6:
			sendChar(pageoffset + 5);
			break;
		case KeyEvent.KEYCODE_7:
			sendChar(pageoffset + 6);
			break;
		case KeyEvent.KEYCODE_8:
			sendChar(pageoffset + 7);
			break;
		case KeyEvent.KEYCODE_9:
			sendChar(pageoffset + 8);
			break;
		case KeyEvent.KEYCODE_0:
			sendChar(pageoffset + 9);
			break;
		case KeyEvent.KEYCODE_STAR:
			pageChange(-1);
			break;
		case KeyEvent.KEYCODE_POUND:
			pageChange(1);
			break;
		}
	}

	protected void doShow(View v) {
		// based on http://stackoverflow.com/a/13962770
		started = true;
		Window win = getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.token = v.getWindowToken();
		lp.y = 1000; 
		lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
		win.setAttributes(lp);
		win.setBackgroundDrawableResource(R.drawable.win_bg);
		win.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		updateButtons();
		try {
			show();
		} catch (Exception e) {
			Log.e("AbsSymDialog", "Cannot create Dialog:");
			Log.e("AbsSymDialog", Arrays.toString(e.getStackTrace()));
		}
	}

}
