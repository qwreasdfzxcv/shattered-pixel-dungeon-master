/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.windows;

import android.app.Activity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;

import org.json.simple.parser.JSONParser;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

//This class makes use of the android EditText component to handle text input
//TODO externalize android-specific code to SPD-classes
public class WndTextInput extends Window {

	String URL = "http://127.0.0.1/test-android/index.php";
	JSONParser jsonParser = new JSONParser();

	int i = 0;

	private EditText textInput;
	private EditText textInput2;

	private static final int WIDTH = 120;
	private static final int HEIGHT = 83;
	private static final int W_LAND_MULTI = 200; //in the specific case of multiline in landscape
	private static final int MARGIN = 2;
	private static final int BUTTON_HEIGHT = 16;

	//default maximum lengths for inputted text
	private static final int MAX_LEN_SINGLE = 20;
	private static final int MAX_LEN_MULTI = 2000;

	public WndTextInput(String title1, String title2, String initialValue1, String initialValue2, boolean multiLine, String posTxt, String negTxt) {
		this(title1, title2, initialValue1, initialValue2, multiLine ? MAX_LEN_MULTI : MAX_LEN_SINGLE, multiLine, posTxt, negTxt);
	}

	public WndTextInput(final String title1, final String title2, final String initialValue1, final String initialValue2, final int maxLength,
						final boolean multiLine, final String posTxt, final String negTxt) {
		super();

		//need to offset to give space for the soft keyboard
		if (SPDSettings.landscape()) {
			offset(multiLine ? -45 : -45);
		} else {
			offset(multiLine ? -60 : -45);
		}

		final int width;
		if (SPDSettings.landscape() && multiLine) {
			width = W_LAND_MULTI; //more editing space for landscape users
		} else {
			width = WIDTH;
		}

		ShatteredPixelDungeon.instance.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				RenderedTextMultiline txtTitle1 = PixelScene.renderMultiline(title1, 9);
				txtTitle1.maxWidth(width);
				txtTitle1.hardlight(Window.TITLE_COLOR);
				txtTitle1.setPos(1, 0);
				add(txtTitle1);

				RenderedTextMultiline txtTitle2 = PixelScene.renderMultiline(title2, 9);
				txtTitle2.maxWidth(width);
				txtTitle2.hardlight(Window.TITLE_COLOR);
				txtTitle2.setPos(1, 31);
				add(txtTitle2);

				float pos = txtTitle2.bottom() + MARGIN;

				textInput = new EditText(ShatteredPixelDungeon.instance);
				textInput.setText(initialValue1);
				textInput.setTypeface(RenderedText.getFont());
				textInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
				textInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

				textInput2 = new EditText(ShatteredPixelDungeon.instance);
				textInput2.setText(initialValue2);
				textInput2.setTypeface(RenderedText.getFont());
				textInput2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
				textInput2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

				//this accounts for the game resolution differing from the display resolution in power saver mode
				final float scaledZoom;
				scaledZoom = camera.zoom * (Game.dispWidth / (float) Game.width);

				//sets different visual style depending on whether this is a single or multi line input.
				final float inputHeight;
				if (multiLine) {

					textInput.setSingleLine(false);
					textInput2.setSingleLine(false);
					//This is equivalent to PixelScene.renderText(6)
					textInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, 6 * scaledZoom);
					textInput2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 6 * scaledZoom);
					//8 lines of text (+1 line for padding)
					inputHeight = 9 * textInput.getLineHeight() / scaledZoom;

				} else {

					//sets to single line and changes enter key input to be the same as the positive button
					textInput.setSingleLine();
					textInput2.setSingleLine();

					textInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
							onSelect(true);
							return true;
						}
					});
					textInput2.setOnEditorActionListener(new EditText.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
							onSelect(true);
							return true;
						}
					});

					//doesn't let the keyboard take over the whole UI
					textInput.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
					textInput2.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

					//centers text
					textInput.setGravity(Gravity.CENTER);
					textInput2.setGravity(Gravity.CENTER);

					//This is equivalent to PixelScene.renderText(9)
					textInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, 9 * scaledZoom);
					textInput2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 9 * scaledZoom);
					//1 line of text (+1 line for padding)
					inputHeight = 2 * textInput.getLineHeight() / scaledZoom;

				}

				//We haven't added the textInput yet, but we can anticipate its height at this point.
				pos += inputHeight + MARGIN;

				RedButton positiveBtn = new RedButton(posTxt) {
					@Override
					protected void onClick() {
						onSelect(true);

//						AttemptLogin attemptLogin = new AttemptLogin();
//						attemptLogin.execute(textInput.getText().toString(), textInput2.getText().toString(), "");

//						hide();
					}
				};
				if (negTxt != null)
					positiveBtn.setRect(MARGIN, pos, (width - MARGIN * 3) / 2, BUTTON_HEIGHT);
				else
					positiveBtn.setRect(MARGIN, pos, width - MARGIN * 2, BUTTON_HEIGHT);
				add(positiveBtn);

				if (negTxt != null) {
					RedButton negativeBtn = new RedButton(negTxt) {
						@Override
						protected void onClick() {
							onSelect(false);

							String url = "http://127.0.0.1/test-android/index.php";

							try {
								OkHttpClient client = new OkHttpClient();
								RequestBody body = new FormBody.Builder().add("username", textInput.getText().toString()).build();
								RequestBody body2 = new FormBody.Builder().add("password", textInput2.getText().toString()).build();

								Request request = new Request.Builder().url(url).post(body).build();
								Request request2 = new Request.Builder().url(url).post(body2).build();

								Response response = client.newCall(request).execute();
								Response response2 = client.newCall(request2).execute();

								ResponseBody responseBody = response.body();
								ResponseBody responseBody2 = response2.body();

								String res = responseBody.toString();
								String res2 = responseBody2.toString();

							} catch (IOException e) {
								e.printStackTrace();
							}

//							AttemptLogin attemptLogin = new AttemptLogin();
//							attemptLogin.execute(textInput.getText().toString(), textInput2.getText().toString());

//							hide();
						}
					};
					negativeBtn.setRect(positiveBtn.right() + MARGIN, pos, (width - MARGIN * 3) / 2, BUTTON_HEIGHT);
					add(negativeBtn);
				}

				pos += BUTTON_HEIGHT + MARGIN;

				//The layout of the TextEdit is in display pixel space, not ingame pixel space
				// resize the window first so we can know the screen-space coordinates for the text input.
				resize(width, HEIGHT);
				final int inputTop = (int) (camera.cameraToScreen(0, txtTitle1.bottom() + MARGIN).y * (Game.dispWidth / (float) Game.width));
				final int inputTop2 = (int) (camera.cameraToScreen(0, txtTitle2.bottom() + MARGIN).y * (Game.dispWidth / (float) Game.width));

				//The text input exists in a separate view ontop of the normal game view.
				// It visually appears to be a part of the game window but is infact a separate
				// UI element from the game entirely.
				FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(
						(int) ((width - MARGIN * 2) * scaledZoom),
						(int) (inputHeight * scaledZoom),
						Gravity.CENTER_HORIZONTAL);
				layout.setMargins(0, inputTop, 0, 0);
				ShatteredPixelDungeon.instance.addContentView(textInput, layout);

				FrameLayout.LayoutParams layout2 = new FrameLayout.LayoutParams(
						(int) ((width - MARGIN * 2) * scaledZoom),
						(int) (inputHeight * scaledZoom),
						Gravity.CENTER_HORIZONTAL);
				layout2.setMargins(0, inputTop2, 0, 0);
				ShatteredPixelDungeon.instance.addContentView(textInput2, layout2);
			}
		});
	}

	public String getText() {
		return textInput.getText().toString().trim();
	}

	public String getText2() {
		return textInput2.getText().toString().trim();
	}

	protected void onSelect(boolean positive) {
	}

	;

	@Override
	public void destroy() {
		super.destroy();
		if (textInput != null && textInput2 != null) {
			ShatteredPixelDungeon.instance.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//make sure we remove the edit text and soft keyboard
					((ViewGroup) textInput.getParent()).removeView(textInput);
					((ViewGroup) textInput2.getParent()).removeView(textInput2);

					InputMethodManager imm = (InputMethodManager) ShatteredPixelDungeon
							.instance.getSystemService(Activity.INPUT_METHOD_SERVICE);
					InputMethodManager imm2 = (InputMethodManager) ShatteredPixelDungeon
							.instance.getSystemService(Activity.INPUT_METHOD_SERVICE);

					imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);
					imm2.hideSoftInputFromWindow(textInput2.getWindowToken(), 0);

					//Soft keyboard sometimes triggers software buttons, so make sure to reassert immersive
					ShatteredPixelDungeon.updateSystemUI();

					textInput = null;
					textInput2 = null;
				}
			});
		}
	}

//	private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected JSONObject doInBackground(String... args) {
//
//			String password = args[1];
//			String name = args[0];
//
//			ArrayList params = new ArrayList();
//			params.add(new BasicNameValuePair("username", name));
//			params.add(new BasicNameValuePair("password", password));
//
//			JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
//
//			return json;
//		}

//		protected void onPostExecute(JSONObject result) {
//			//dismiss the dialog once product deleted
//			//Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
//
//			try {
//				if(result != null) {
//					Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
//				} else {
//					Toast.makeText(getApplicationCOntext(),"Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
//				}
//			} catch(JSONException e) {
//				e.printStackTrace();
//			}
//		}
//}
}
