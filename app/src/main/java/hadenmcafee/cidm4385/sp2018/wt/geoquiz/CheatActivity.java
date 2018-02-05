package hadenmcafee.cidm4385.sp2018.wt.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity
{
    private static final String EXTRA_ANSWER_IS_TRUE = "hadenmcafee.cidm4385.sp2018.wt.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "hadenmcafee.cidm4385.sp2018.wt.geoquiz.answer_shown";
    private static final String KEY_CHEATED = "cheated";

    private boolean mAnswerIsTrue;
    private boolean mCheated;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    private static final String TAG = "QuizActivity";

    public static Intent newIntent(Context packageContext, boolean answerIsTrue)
    {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        TextView mAPIdescription;
        Log.d(TAG, "1. mCheated = " + mCheated);
        mCheated = false;
        Log.d(TAG, "2. mCheated = " + mCheated);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null)
        {
            mCheated = savedInstanceState.getBoolean(KEY_CHEATED, false);
            Log.d(TAG, "CheatActivity:OnCreate - mCheated = " + mCheated);
            if(mCheated)
            {
                setAnswerShownResult(true);
            }
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mAnswerIsTrue)
                {
                    mAnswerTextView.setText(R.string.true_button);
                }
                else
                {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mCheated = true;
                setAnswerShownResult(true);

                //add animation
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) //ensure compatibility
                {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }
                else
                {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        mAPIdescription = (TextView) findViewById(R.id.api_version);
        mAPIdescription.setText("API Level " + Build.VERSION.SDK_INT);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "was saved instance (CheatActivity), isCheated = " + mCheated);
        savedInstanceState.putBoolean(KEY_CHEATED, mCheated);
    }

    private void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result)
    {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
