package com.wise.soar.menu;

import com.wise.soar.level.Level;

public abstract class SubMenu extends Menu {
	private Button back;
	protected Menu parent;

	public SubMenu(Menu menu, Level level) {
		super(level);
		parent = menu;

		back = new Button(10, 10, "");
		back.setType(Button.TYPE_BACK);
		back.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				close();
				parent.open();
			}
		});

		add(back);
	}

	protected abstract void struct();

	public void goBack() {
		back.fireEvent();
	}
}