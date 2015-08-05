# ScoreView
android score view

<h1><b>Usage:</b></h1>

```
dependencies {
    compile 'com.githab.svyatoslavlynda.scoreview:scoreview:0.0.1'
}
```

layout:

```
<com.githab.svyatoslavlynda.scoreview.ScoreView
	android:id="@+id/score_board"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:layout_gravity="center"/>
```

Activity:

```
final ScoreView scoreBoard = (ScoreView) root.findViewById(R.id.score_board);
scoreBoard.startAnimation(scoreAngel < 90 ? scoreAngel - 90 : scoreAngel - 90);
```

![alt tag](https://raw.githubusercontent.com/SvyatoslavLynda/ScoreView/master/scoreview.png)
