package com.packtpub.kitchendroid;

import android.app.Activity;
import android.os.Bundle;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.view.View.OnClickListener;

public class QuestionActivity extends Activity implements OnClickListener
{
	private Button[] buttons;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		initQuestionScreen();
    }

	private static int getQuestionID(Resources res, int index) {
		final String[] questions = res.getStringArray(R.array.questions);
		return res.getIdentifier(
			questions[index],
			"array",
			"com.packtpub.kitchendroid");
	}

	private int getQuestionIndex() {
		return getIntent().getIntExtra("KitchenDroid.Question", 0);
	}
 
	private void initQuestionScreen() {
		TextView question = (TextView) findViewById(R.id.question);
		ViewGroup answers = (ViewGroup) findViewById(R.id.answers);
		
		final Resources resources = getResources();
		
		int questionID = getQuestionID(resources, getQuestionIndex());
		String[] questionData = resources.getStringArray(questionID);
		
		question.setText(questionData[0]);
		
		int answerCount = questionData.length - 1;
		buttons = new Button[answerCount];
		
		for (int i = 0; i < answerCount; i++) {
			String answer = questionData[i + 1];
			Button button = new Button(this);
			button.setText(answer);
			answers.addView(button);
			
			button.setOnClickListener(this);
			
			buttons[i] = button;
		}
	}

   public void onClick(final View clicked) {
        final String[] questions = getResources().getStringArray(
                R.array.questions);

        if(getQuestionIndex() + 1 >= questions.length) {
            Toast.makeText(
                    this,
                    R.string.noMoreQuestions,
                    Toast.LENGTH_SHORT).show();
        } else {
            final Intent intent = new Intent(
                    QuestionActivity.this,
                    QuestionActivity.class);

            intent.putExtra("KitchenDroid.Question", getQuestionIndex() + 1);

            startActivity(intent);
        }
    }
	
}
