package com.example.qu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateQuestActivity extends AppCompatActivity {

    private TextView countTextView;
    private TextView questionTextView;
    private EditText answer;

    private Button nextButton;
    private Button prevButton;

    private int count = 1;
    private int pos = 0;
    private Quest[] quests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quest);

        quests = new Quest[]{
                new Quest("Math Riddles", "Everywhere", new Task[]{
                        new Task("4, 8, 16, ?", "32"),
                        new Task("6 = 30\n3 = 15\n7 = 35\n2 = ?", "10"),
                        new Task("4, 11, 18, ?", "25"),
                        new Task("8 - 8 / 4 * 3 = ?", "2"),
                        new Task("A + B = 60\nA - B = 40\nA / B = ?", "5"),
                        new Task("7, 15, 31, ?", "63")
                }),
                new Quest("Deathcore metal", "Lviv", new Task[]{
                        new Task("1783, 3178, 8317, ?", "7831")
                }),
                new Quest("Deathcore metal", "Kyiv", new Task[]{
                        new Task("8 = 17\n22 = 45\n15 = 31\n20 = ?", "41"),
                        new Task("6, 5 = 33\n7, 2 = 17\n11, 4 = 47\n3, 8 = ?", "27")
                })
        };

        Intent intent = getIntent();
        pos = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, -1);
        countTextView = (TextView) findViewById(R.id.titleTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);

        answer = (EditText) findViewById(R.id.answerEditText);
        prevButton = (Button) findViewById(R.id.prevButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        changeTask(pos);

        prevButton.setEnabled(false);
        if (quests[pos].tasks.length <= 1) {
            nextButton.setEnabled(false);
        }

//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (answer.getText().toString() == quests[pos].tasks[count-1].answer) {
//                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "No :(", Toast.LENGTH_SHORT);
//                }
//                count += 1;
//                changeTask(pos);
//            }
//        });

//        prevButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                count -= 1;
//                changeTask(pos);
//            }
//        });
    }

    public void nextClick(View v) {
        Toast toast;
        if (answer.getText().toString().equals(quests[pos].tasks[count - 1].answer)) {
            toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
        }
        else {
            toast = Toast.makeText(getApplicationContext(), "No :(", Toast.LENGTH_SHORT);
        }
        toast.show();
        count += 1;
        changeTask(pos);
    }

    public void prevClick(View v) {
        count -= 1;
        changeTask(pos);
    }

    private void changeTask(int pos) {
        String countLabel = "Task " + count;
        countTextView.setText(countLabel);
        questionTextView.setText(quests[pos].tasks[count-1].question);
        if (!answer.getText().toString().equals(""))
            answer.getText().clear();

        if (count == 1) {
            prevButton.setEnabled(false);
        }
        else if (count > 1) {
            prevButton.setEnabled(true);
        }

        if (count == quests[pos].tasks.length) {
            nextButton.setEnabled(false);
        }
        else if (count < quests[pos].tasks.length) {
            nextButton.setEnabled(true);
        }
    }
}