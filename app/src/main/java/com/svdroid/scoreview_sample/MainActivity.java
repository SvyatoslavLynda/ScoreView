package com.svdroid.scoreview_sample;

import android.app.Activity;
import android.os.Bundle;

import com.svdroid.scoreview.ScoreView;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ScoreView scoreView = (ScoreView) findViewById(R.id.score_view);
		scoreView.startAnimation(50);
	}
}
