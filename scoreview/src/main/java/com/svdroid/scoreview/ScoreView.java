package com.svdroid.scoreview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class ScoreView extends View
{
	private Paint _paint;
	private RectF _main;
	private Paint[] _arcsPaint;
	private Path[] _scoreTitles;
	private Paint _circlePaint;
	private Paint _textPaint;
	private Bitmap _hand;
	private int _startAngel = -90;
	private int _endAngel;
	private Timer _timer;

	public ScoreView(Context context)
	{
		super(context);
		_init();
	}

	public ScoreView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		_init();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawArc(_main, -180, 29, true, _arcsPaint[0]);
		canvas.drawArc(_main, -150, 29, true, _arcsPaint[1]);
		canvas.drawArc(_main, -120, 29, true, _arcsPaint[2]);
		canvas.drawArc(_main, -90, 29, true, _arcsPaint[3]);
		canvas.drawArc(_main, -60, 29, true, _arcsPaint[4]);
		canvas.drawArc(_main, -30, 30, true, _arcsPaint[5]);
		canvas.drawArc(_main, -180, 180, false, _paint);
		canvas.drawCircle(
			getWidth() / 2,
			getWidth() / 2,
			getResources().getDimensionPixelSize(R.dimen.circle_radius),
			_circlePaint
		);

		final String[] titles = getResources().getStringArray(R.array.score_titles);

		for (int i = 0; i < titles.length; i++) {
			canvas.drawTextOnPath(titles[i], _scoreTitles[i], 0, 0, _textPaint);
		}

		canvas.save();

		canvas.rotate(
			_startAngel,
			_main.centerX(),
			_main.centerY()
		);
		canvas.drawBitmap(
			_hand,
			_main.centerX() - _hand.getWidth() / 2,
			_main.centerY() - _hand.getHeight() / 2 - (3 * _hand.getWidth() / 4),
			_circlePaint
		);

		canvas.restore();

		super.onDraw(canvas);
	}

	public void startAnimation(int endAngel)
	{
		if (endAngel > 90) {
			_endAngel = 90;
		} else if (endAngel < -90) {
			_endAngel = -90;
		} else {
			_endAngel = endAngel;
		}

		_timer = new Timer();
		_timer = new Timer();
		final AnimationTask animationTask = new AnimationTask();
		_timer.schedule(animationTask, 0, 24);
	}

	private void _init()
	{
		final Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
		final int width;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			final Point size = new Point();
			display.getSize(size);
			width = size.x;
		} else {
			//noinspection deprecation
			width = display.getWidth();
			//noinspection deprecation
		}

		final int mainMargin = getResources().getDimensionPixelSize(R.dimen.margin_half_circle);
		_paint = new Paint();
		_paint.setColor(Color.GREEN);
		_paint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.title_score_size));
		_paint.setAntiAlias(true);
		_paint.setStrokeCap(Paint.Cap.BUTT);
		_paint.setStyle(Paint.Style.STROKE);
		_paint.setShader(
			new RadialGradient(
				width / 2,
				width / 2,
				width / 2,
				Color.GRAY,
				getResources().getColor(R.color.arc_white_gray),
				Shader.TileMode.MIRROR
			)
		);

		_arcsPaint = new Paint[6];
		final int[] arcsColor = getResources().getIntArray(R.array.arcs);

		for (int i = 0; i < arcsColor.length; i++) {
			_arcsPaint[i] = new Paint();
			_arcsPaint[i].setColor(arcsColor[i]);
			_arcsPaint[i].setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.title_score_size));
			_arcsPaint[i].setAntiAlias(true);
		}

		_main = new RectF(mainMargin, mainMargin, width - mainMargin, width - mainMargin);

		_scoreTitles = new Path[6];

		for (int i = 0; i < _scoreTitles.length; i++) {
			_scoreTitles[i] = new Path();
		}

		_scoreTitles[0].addArc(_main, -180, 29);
		_scoreTitles[1].addArc(_main, -150, 29);
		_scoreTitles[2].addArc(_main, -120, 29);
		_scoreTitles[3].addArc(_main, -90, 29);
		_scoreTitles[4].addArc(_main, -60, 29);
		_scoreTitles[5].addArc(_main, -30, 30);

		_textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		_textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		_textPaint.setColor(Color.BLACK);
		_textPaint.setTextSize(getResources().getDimension(R.dimen.text_score_size));
		_textPaint.setTextAlign(Paint.Align.CENTER);

		_circlePaint = new Paint();
		_circlePaint.setAntiAlias(true);
		_circlePaint.setColor(getResources().getColor(R.color.window_bg));
		_circlePaint.setStyle(Paint.Style.FILL);

		_hand = BitmapFactory.decodeResource(getResources(), R.drawable.hand);
	}

	private class AnimationTask extends TimerTask
	{

		@Override
		public void run()
		{
			if (_startAngel < _endAngel) {
				_startAngel++;
				postInvalidate();
			} else {
				if (_timer != null) {
					_timer.cancel();
					_timer.purge();
					_timer = null;
				}
			}
		}
	}
}