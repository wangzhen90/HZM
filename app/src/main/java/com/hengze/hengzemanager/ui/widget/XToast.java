/*
 * Copyright (C) 2014 The XToast Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hengze.hengzemanager.ui.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengze.hengzemanager.R;
import java.util.LinkedList;

/**
 * Custom toast, simplified version of SuperToasts.
 * <p/>
 * Now supports the following configurations:</br> 1. Text size/color.</br> 2.
 * Duration 3. Background resource/color.</br> 4. Animation.</br> 5. Gravity and
 * offset.</br> 6. Cover previous toast.</br> 7. Button
 * integration(text/ic_launcher/event).</br> 8. Position relative to a specified view.
 * 
 * @author knight
 */
public class XToast {
	private Context mContext;

	private View mXToastView;
	private LinearLayout mRootLayout;
	private TextView mTextView;
	private Button mButton;

	private WindowManager mWindowManager;
	private LayoutParams mWindowManagerLayoutParams;

	private int mDuration;
	private boolean mCover;

	// only have effect when withButton(...) is called
	private boolean mDismissWhenTouchOutside;

	/**
	 * Create a XToast instance.
	 * <p/>
	 * The toast will dismiss immediately when your Activity destroyed if you
	 * use the context with Activity scope, just pass the application context
	 * instead to change this behavior.
	 * 
	 * @param ctx
	 *            null is disallowed.
	 * @param text
	 *            null is allowed for integrate a single button with no toast
	 *            text.
	 * @return
	 */
	public static XToast create(Context ctx, CharSequence text) {
		if (ctx == null) {
			throw new IllegalArgumentException("Context is null");
		}
		if (text == null || text.length() == 0) {
			return new XToast(ctx);
		} else {
			return new XToast(ctx).withText(text).withTextColor(
					ctx.getResources().getColor(R.color.xtoast_text));
		}
	}

	/**
	 * A static version for dismiss all the toasts.
	 */
	public static void dismissAll() {
		XToastQueue.getInstance().dequeueAll();
	}

	/**
	 * A static version for dismiss current toast.
	 */
	public static void dismissCurrent() {
		XToastQueue.getInstance().dequeueCurrent();
	}

	private XToast(Context ctx) {
		mContext = ctx;
		initViews();
	}

	private void initViews() {
		mXToastView = LayoutInflater.from(mContext).inflate(R.layout.xtoast,
				null);
		if (mXToastView != null) {
			mRootLayout = (LinearLayout) mXToastView
					.findViewById(R.id.xtoast_root_layout);
			mTextView = (TextView) mXToastView.findViewById(R.id.xtoast_text);
			mWindowManager = (WindowManager) mContext
					.getSystemService(Context.WINDOW_SERVICE);
			mWindowManagerLayoutParams = new LayoutParams();
		}
	}

	private void configure() {
		if (mDuration == 0) {
			mDuration = Duration.SHORT;
		}
		mWindowManagerLayoutParams.height = LayoutParams.WRAP_CONTENT;
		mWindowManagerLayoutParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		mWindowManagerLayoutParams.type = LayoutParams.TYPE_TOAST;
		mWindowManagerLayoutParams.format = PixelFormat.TRANSLUCENT;
		if (mWindowManagerLayoutParams.gravity == 0
				&& mWindowManagerLayoutParams.x == 0
				&& mWindowManagerLayoutParams.y == 0) {
			mWindowManagerLayoutParams.gravity = Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL;
		}
		if (mWindowManagerLayoutParams.windowAnimations == 0) {
			mWindowManagerLayoutParams.windowAnimations = Anim.TOAST;
		}
		if (mButton == null) {
			mWindowManagerLayoutParams.width = LayoutParams.WRAP_CONTENT;
			mWindowManagerLayoutParams.flags |= LayoutParams.FLAG_NOT_TOUCHABLE;
		} else {
			mWindowManagerLayoutParams.width = LayoutParams.MATCH_PARENT;
			mWindowManagerLayoutParams.flags |= LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
			if (TextUtils.isEmpty(mTextView.getText())) {
				mTextView.setVisibility(View.GONE);
				mXToastView.findViewById(R.id.xtoast_splitter).setVisibility(
						View.GONE);
			}
			if (mDismissWhenTouchOutside) {
				mXToastView.setTag(this);
				mXToastView.setOnTouchListener(mTouchListener);
			}
		}
	}

	public void show() {
		configure();
		XToastQueue.getInstance().enqueue(this);
	}

	public void dismiss() {
		XToastQueue.getInstance().dequeue(this);
	}

	public boolean isShown() {
		return mXToastView != null && mXToastView.isShown();
	}

	public XToast withText(CharSequence text) {
		mTextView.setText(text);
		return this;
	}

	public XToast withTextColor(int color) {
		mTextView.setTextColor(color);
		return this;
	}

	public XToast withTextSize(int size) {
		mTextView.setTextSize(size);
		return this;
	}

	public XToast withBackgroundResource(int res) {
		mRootLayout.setBackgroundResource(res);
		return this;
	}

	public XToast withBackgroundColor(int color) {
		mRootLayout.setBackgroundColor(color);
		return this;
	}

	public XToast withDuration(int duration) {
		mDuration = duration;
		return this;
	}

	/**
	 * Use a style resource for this toast's animation.
	 * <p/>
	 * Must be a system resource. It means you can not define your own animation
	 * resource to use.
	 * <p/>

	 * <p/>
	 * More animations see android.R.style.Animation.xxx
	 * 
	 * @param animation
	 * @return
	 */
	public XToast withAnimation(int animation) {
		mWindowManagerLayoutParams.windowAnimations = animation;
		return this;
	}

	/**
	 * Set the gravity for this toast.
	 * 
	 * @param gravity
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	public XToast withGravity(int gravity, int xOffset, int yOffset) {
		mWindowManagerLayoutParams.gravity = gravity;
		mWindowManagerLayoutParams.x = xOffset;
		mWindowManagerLayoutParams.y = yOffset;
		return this;
	}

	/**
	 * Show toast relative to a specified view for this toast's left-top axis.
	 * <p/>
	 * After calling this method, do not call withGravity() anymore because they
	 * could be messed up.
	 * 
	 * @param view
	 * @param position
	 *
	 *            relative positions.
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	public XToast withPosition(View view, int position, int xOffset, int yOffset) {
		int[] pos = new int[2];
		view.getLocationOnScreen(pos);
		switch (position) {
		case Position.LEFT | Position.TOP:
			xOffset += pos[0];
			yOffset += pos[1];
			break;

		case Position.LEFT | Position.CENTER:
			xOffset += pos[0];
			yOffset += pos[1] + view.getHeight() / 2;
			break;

		case Position.LEFT | Position.BOTTOM:
			xOffset += pos[0];
			yOffset += pos[1] + view.getHeight();
			break;

		case Position.CENTER | Position.TOP:
			xOffset += pos[0] + view.getWidth() / 2;
			yOffset += pos[1];
			break;

		case Position.CENTER:
			xOffset += pos[0] + view.getWidth() / 2;
			yOffset += pos[1] + view.getHeight() / 2;
			break;

		case Position.CENTER | Position.BOTTOM:
			xOffset += pos[0] + view.getWidth() / 2;
			yOffset += pos[1] + view.getHeight();
			break;

		case Position.RIGHT | Position.TOP:
			xOffset += pos[0] + view.getWidth();
			yOffset += pos[1];
			break;

		case Position.RIGHT | Position.CENTER:
			xOffset += pos[0] + view.getWidth();
			yOffset += pos[1] + view.getHeight() / 2;
			break;

		case Position.RIGHT | Position.BOTTOM:
			xOffset += pos[0] + view.getWidth();
			yOffset += pos[1] + view.getHeight();
			break;

		case Position.LEFT:
		case Position.TOP:
			// equals to (LEFT | TOP)
			xOffset += pos[0];
			yOffset += pos[1];
			break;

		case Position.RIGHT:
			// equals to (RIGHT | TOP)
			xOffset += pos[0] + view.getWidth();
			yOffset += pos[1];
			break;

		case Position.BOTTOM:
			// equals to (LEFT | BOTTOM)
			xOffset += pos[0];
			yOffset += pos[1] + view.getHeight();
			break;
		}
		return withGravity(Gravity.TOP | Gravity.LEFT, xOffset, yOffset);
	}

	/**
	 * Whether remove and dismiss all the other toasts before this one shows.
	 * 
	 * @param cover
	 *            if true all the other toasts in the queue will be removed and
	 *            dismissed.
	 * @return
	 */
	public XToast withCover(boolean cover) {
		mCover = cover;
		return this;
	}

	/**
	 * Integrate a button in the toast.
	 * <p/>
	 * The text and ic_launcher params could be null or empty without any exceptions
	 * thrown.
	 * <p/>
	 * {text=null} means the button does not have text(Probably only has an
	 * ic_launcher)
	 * <p/>
	 * {text=empty} means the button has an empty text.
	 * <p/>
	 * {ic_launcher=null} means the button does not have ic_launcher(Probably only has text)
	 * <p/>
	 * {text=null && ic_launcher=null} means no button.
	 * 
	 * @param text
	 *            the button text
	 * @param icon
	 *            the button ic_launcher
	 * @param listener
	 *            click listener for the button(not for the whole toast)
	 * @param dismissWhenTouchOutside
	 *            whether to dismiss the toast when touch outside the toast
	 * @return
	 */
	public XToast withButton(CharSequence text, Drawable icon,
			OnButtonClickListener listener, boolean dismissWhenTouchOutside) {
		if (text == null && icon == null) {
			return this;
		}
		if (mXToastView == null) {
			return this;
		}
		((ViewStub) mXToastView.findViewById(R.id.xtoast_viewstub)).inflate();
		mButton = (Button) mXToastView.findViewById(R.id.xtoast_button);
		if (mButton != null) {
			mDismissWhenTouchOutside = dismissWhenTouchOutside;
			if (text != null) {
				mButton.setText(text);
				mButton.setTextColor(mContext.getResources().getColor(
						R.color.xtoast_text));
				mButton.setTextSize(mContext.getResources().getDimension(
						R.dimen.xtoast_button_text));
			}
			if (icon != null) {
				icon.setBounds(0, 0, icon.getIntrinsicWidth(),
						icon.getIntrinsicHeight());
				mButton.setCompoundDrawables(icon, null, null, null);
			}
			mButton.setTag(new Object[] { listener, this });
			mButton.setOnClickListener(mClickListener);
		}
		return this;
	}

	public XToast withButton(CharSequence text, Drawable icon,
			final OnButtonClickListener listener) {
		return withButton(text, icon, listener, false);
	}

	public XToast withButton(CharSequence text, int icon,
			final OnButtonClickListener listener,
			boolean dismissWhenTouchOutside) {
		return withButton(text, mContext.getResources().getDrawable(icon),
				listener, dismissWhenTouchOutside);
	}

	public XToast withButton(CharSequence text, int icon,
			final OnButtonClickListener listener) {
		return withButton(text, mContext.getResources().getDrawable(icon),
				listener, false);
	}

	private static View.OnTouchListener mTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
				Object o = v.getTag();
				if (o != null && o instanceof XToast) {
					XToastQueue.getInstance().dequeue((XToast) o);
				}
				return true;
			}
			return false;
		}
	};

	private static View.OnClickListener mClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Object o = v.getTag();
			if (o != null && o instanceof Object[]) {
				OnButtonClickListener l = (OnButtonClickListener) ((Object[]) o)[0];
				XToast x = (XToast) ((Object[]) o)[1];
				if (l != null && x != null) {
					l.onClick(x);
				}
			}
		}
	};

	/**
	 * How long the toast will be show in milliseconds.
	 */
	public static class Duration {
		public static final int SHORT = 2000;
		public static final int LONG = 4000;
		public static final int FOREVER = -1;
	}

	/**
	 * Built-in animations.
	 */
	public static class Anim {
		public static final int TOAST = android.R.style.Animation_Toast;
		public static final int SCALE = android.R.style.Animation_Dialog;
		public static final int POPUP = android.R.style.Animation_InputMethod;
		public static final int FLY = android.R.style.Animation_Translucent;
	}

	/**
	 * Use for withPosition() method to specify the view's position for the
	 * toast to show.
	 */
	public static class Position {
		public static final int LEFT = 0x0001;
		public static final int TOP = LEFT << 1;
		public static final int RIGHT = LEFT << 2;
		public static final int BOTTOM = LEFT << 3;
		public static final int CENTER = LEFT << 4;
	}

	private static class XToastQueue extends Handler {
		private static XToastQueue instance;
		private final LinkedList<XToast> queue = new LinkedList<XToast>();

		private static XToastQueue getInstance() {
			if (instance == null) {
				synchronized (XToastQueue.class) {
					if (instance == null) {
						instance = new XToastQueue();
					}
				}
			}
			return instance;
		}

		private XToastQueue() {
		}

		private void enqueue(XToast xtoast) {
			if (xtoast != null) {
				if (xtoast.mCover) {
					XToast x;
					synchronized (queue) {
						x = queue.peek();
						queue.clear();
						queue.add(xtoast);
					}
					if (x != null) {
						dismiss(x);
					}
				} else {
					synchronized (queue) {
						queue.add(xtoast);
					}
				}
			}
			next();
		}

		private void dequeue(XToast xtoast) {
			if (xtoast != null) {
				synchronized (queue) {
					queue.remove(xtoast);
				}
				dismiss(xtoast);
			}
			next();
		}

		private void dequeueCurrent() {
			XToast x;
			synchronized (queue) {
				x = queue.poll();
			}
			if (x != null) {
				dismiss(x);
			}
			next();
		}

		private void dequeueAll() {
			XToast x;
			synchronized (queue) {
				x = queue.peek();
				queue.clear();
			}
			if (x != null) {
				dismiss(x);
			}
		}

		private void next() {
			XToast x;
			synchronized (queue) {
				x = queue.peek();
			}
			if (x != null) {
				if (x.mDuration == Duration.FOREVER) {
					synchronized (queue) {
						queue.remove(x);
					}
				}
				if (!x.isShown()) {
					show(x);
				}
			}
		}

		private void show(XToast xtoast) {
			if (xtoast != null) {
				WindowManager wm = xtoast.mWindowManager;
				LayoutParams p = xtoast.mWindowManagerLayoutParams;
				View v = xtoast.mXToastView;
				if (wm != null && p != null && v != null) {
					wm.addView(v, p);
					if (xtoast.mDuration != Duration.FOREVER) {
						Message msg = Message.obtain(this, 0, xtoast);
						if (msg != null) {
							sendMessageDelayed(msg, xtoast.mDuration + 200);
						}
					}
				}
			}
		}

		private void dismiss(XToast xtoast) {
			if (xtoast == null) {
				return;
			}
			WindowManager wm = xtoast.mWindowManager;
			View v = xtoast.mXToastView;
			if (wm != null && v != null && xtoast.isShown()) {
				wm.removeView(v);
			}
		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				dequeue((XToast) msg.obj);
			}
		}
	}

	public static interface OnButtonClickListener {
		void onClick(XToast xtoast);
	}
}
