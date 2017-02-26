package org.nyanya.android.traditionalt9;


import android.content.Context;
import android.view.View;

public class SmileyDialog extends AbsSymDialog {

	private static final String[] symbols = {
		":-)", ";-D", "X-D", ":-P", ":-|", ":-(", ":-X", ":-B", ":'-(", ":_(",
		"(*_*)", "<3", "</3", "@}~>~~", ":-*", "X-D", ":@", "o_0",  ">:-)", "/:-]",
		"( _ )", "●～*", "X-(", "(*¬*)", "d(^_^)b", "<Л:-0", "0:-)", "=:[]", ":-]", ":-Q",
		":->", ":-I", ":-0", "=^.^=", ":o3", "=8)", ":o)", "B^D", " :-!", "{}",
		"(-_-;)",  "=p", "(-.-)Zzz…", "(^^)/~~~", "%-)", "☆彡 ☆ミ", ":*)", "o○", "[i]",  ":-#", };


	private static final int MAX_PAGE = (int) Math.ceil(symbols.length / 10.0);
	
	public SmileyDialog(Context c, View mv) {
		super(c, mv);
        
	}

	@Override
	protected String getSymbol(int index) {
		return symbols[index];
	}

	@Override
	protected String getTitleText() {
		return "Smiley";
	}

	@Override
	protected int getSymbolSize() {
		return symbols.length;
	}

	@Override
	protected int getMaxPage() {
		return MAX_PAGE;
	}

}
