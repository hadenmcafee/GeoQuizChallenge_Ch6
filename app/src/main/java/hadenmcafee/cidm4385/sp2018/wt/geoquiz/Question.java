package hadenmcafee.cidm4385.sp2018.wt.geoquiz;

/**
 * Created by Haden McAfee on 1/24/2018.
 */

public class Question
{
    private int mTextResID;
    private boolean mAnswerTrue;
    private boolean mQuestionTaken;
    private boolean mAnswerGuessed;
    private boolean mCheated;

    public Question(int textResID, boolean answerTrue)
    {
        mTextResID = textResID;
        mAnswerTrue = answerTrue;
        mQuestionTaken = false;
        mAnswerGuessed = false;
        mCheated = false;
    }

    //
    public int getTextResID()
    {
        return mTextResID;
    }

    //
    public void setTextResID(int textResID)
    {
        mTextResID = textResID;
    }

    //
    public boolean isAnswerTrue()
    {
        return mAnswerTrue;
    }

    //
    public void setAnswerTrue(boolean answerTrue)
    {
        mAnswerTrue = answerTrue;
    }
}
