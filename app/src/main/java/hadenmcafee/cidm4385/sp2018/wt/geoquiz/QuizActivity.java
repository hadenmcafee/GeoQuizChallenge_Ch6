package hadenmcafee.cidm4385.sp2018.wt.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class QuizActivity extends AppCompatActivity
{
    //create static variables
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATED = "isCheater";
    private static final String KEY_WAS_CORRECT = "wasCorrect";
    private static final String KEY_IS_TAKEN = "isTaken";
    private static final int REQUEST_CODE_CHEAT = 0;

    //create layout members
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    //create array of questions
    private Question[] mQuestionBank = new Question[]
    {
        new Question(R.string.question_australia, true),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),
                new Question(R.string.question_africa, false),
                new Question(R.string.question_americas, true),
                new Question(R.string.question_asia, true)
    };

    //create arrays to track user activity data
    private boolean[] mQuestionBankData_isTaken = new boolean[6];
    private boolean[] mQuestionBankData_wasCorrect = new boolean[6];
    private boolean[] mQuestionBankData_cheated = new boolean[6];

    //private Boolean mIsCheater = false;

    //create logical members
    private int mCurrentIndex = 0;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "onCreate(Bundle) called");

        if(savedInstanceState!=null)
        {
            //Log.d(TAG, "Was saved instance (QA), isCheater = " + mIsCheater);
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mQuestionBankData_isTaken = savedInstanceState.getBooleanArray(KEY_IS_TAKEN);
            mQuestionBankData_cheated = savedInstanceState.getBooleanArray(KEY_CHEATED);
            mQuestionBankData_wasCorrect = savedInstanceState.getBooleanArray(KEY_WAS_CORRECT);
        }

        setContentView(R.layout.activity_quiz);

        //True button
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            //
            @Override
            public void onClick(View v)
            {
                checkAnswer(true);
                /*Toast correctToast = Toast.makeText(QuizActivity.this, R.string.correct_toast,
                        Toast.LENGTH_SHORT);
                correctToast.setGravity(Gravity.TOP, 0, 500);
                correctToast.show();*/
            }
        });

        //False Button
            //Log.d(TAG, "Still Working, prior to false button onclick");
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            //
            @Override
            public void onClick(View v)
            {
                checkAnswer(false);
                /*Toast incorrectToast = Toast.makeText(QuizActivity.this, R.string.incorrect_toast,
                        Toast.LENGTH_SHORT);
                incorrectToast.setGravity(Gravity.TOP, 0, 500);
                incorrectToast.show();*/
            }
        });

        //Cheat Button
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        //Log.d(TAG, "Still Working, prior to previous button onclick");
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                previousQuestion();
            }
        });

        //Log.d(TAG, "Still Working, prior to next button onclick");
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nextQuestion();
            }
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nextQuestion();
            }
        });

        //Log.d(TAG, "Still Working, prior to update Question");
        updateQuestion();
        //Log.d(TAG, "Still Working, prior to true button onclick");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT)
        {
            if(data == null)
            {
                return;
            }
            mQuestionBankData_cheated[mCurrentIndex]=(CheatActivity.wasAnswerShown(data));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(KEY_CHEATED, mQuestionBankData_cheated);
        savedInstanceState.putBooleanArray(KEY_IS_TAKEN, mQuestionBankData_isTaken);
        savedInstanceState.putBooleanArray(KEY_WAS_CORRECT, mQuestionBankData_wasCorrect);
        //savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d(TAG, "onDestroy called");
    }

    private void previousQuestion()
    {
        if(mCurrentIndex == 0)
        {
            mCurrentIndex = mQuestionBank.length - 1;
        }
        else
        {
            mCurrentIndex = mCurrentIndex - 1;
        }
        updateQuestion();
    }

    private void nextQuestion()
    {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
    }

    public void updateQuestion()
    {
        //Log.d(TAG, "Entering update question");
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
        updateButtonState();
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        //Log.d(TAG, "Entering check answer");
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(mQuestionBankData_cheated[mCurrentIndex])
        {
            messageResId = R.string.judgment_toast;
        }
        else
        {
            if (userPressedTrue == answerIsTrue)
            {
                //set correct message, store correct
                messageResId = R.string.correct_toast;
                mQuestionBankData_wasCorrect[mCurrentIndex]=true;
            }
            else
            {
                //set incorrect message, store incorrect
                messageResId = R.string.incorrect_toast;
                mQuestionBankData_wasCorrect[mCurrentIndex]=false;
            }
        }

        Toast feedbackToast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        feedbackToast.setGravity(Gravity.TOP, 0, 500);
        feedbackToast.show();

        //store question taken
        mQuestionBankData_isTaken[mCurrentIndex]=true;

        //update button state
        updateButtonState();

        //determine if quiz finished
        checkFinished();
    }

    private void updateButtonState()
    {
        //Log.d(TAG, "Entering updateButtonState");
        //Log.d(TAG, "-->updateButtonState: current index = " + mCurrentIndex);
        if(mQuestionBankData_isTaken[mCurrentIndex])
        {
            //Log.d(TAG, "question taken, set disabled");
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
            mCheatButton.setEnabled(false);

        }
        else
        {
            //Log.d(TAG, "question taken, set enabled");
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
            mCheatButton.setEnabled(true);
        }
    }

    private void checkFinished()
    {
            //Log.d(TAG, "Entering checkFinished");
            //Log.d(TAG, "i = " + mCurrentIndex);
        boolean questionRemains = false;

        for(int i = 0; i < mQuestionBank.length; i++)
        {
                //Log.d(TAG, i + ":" + mQuestionBank[i].getQuestionTaken());
            if(!mQuestionBankData_isTaken[i])
            {
                questionRemains = true;
            }
        }

        //Log.d(TAG, "" + questionRemains);
        if(questionRemains == false)
        {
            displayQuizScore();
        }
    }

    void displayQuizScore()
    {
        Log.d(TAG, "Entering displayQuizScore");
        int correctAnswers = 0;
        int messageResId = R.string.display_quiz_score;

        for (int i = 0; i < mQuestionBank.length; i++)
        {
            if(mQuestionBankData_wasCorrect[i])
            {
                correctAnswers++;
            }
        }

        DecimalFormat df = new DecimalFormat("##.##%");
        double percent = ((float)correctAnswers / mQuestionBank.length);
        String formattedPercent = df.format(percent);

        Toast quizScoreToast = Toast.makeText(this, getString(messageResId) + correctAnswers
                + "/" + mQuestionBank.length +  " (" +formattedPercent + ").", Toast.LENGTH_LONG);
        quizScoreToast.setGravity(Gravity.TOP, 0, 500);
        quizScoreToast.show();
    }
}