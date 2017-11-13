package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginMessageActivity extends AppCompatActivity {
    String username;
    Button button;

    Button compareStatsButton;
    Button editPlayerStats;
//    Button createGroupButton;
//    Button editGroupButton;
    Button rankingsButton;
    Button scoreKeepingButton;
//    Button groupStatsButton;
//    Button mergeGroupsButton;
    Button liveTournamentButton;
    Button groupsButton;
    Button allowPlayerEditsButton;
    Button eventHubButton;
    Button messagesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.welcome_message);
        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(message);
        username = textView.getText().toString();
        username = "" + username.substring(0, username.length() - 1);

        button = (Button) findViewById(R.id.continueButton);
        editPlayerStats = (Button) findViewById(R.id.editPlayerStats);
//        createGroupButton = (Button) findViewById(R.id.createGroupButton);
//        editGroupButton = (Button) findViewById(R.id.editGroupButton);
        scoreKeepingButton = (Button) findViewById(R.id.scoreKeepingButton);
//        groupStatsButton = (Button) findViewById(R.id.groupStatsButton);
//        mergeGroupsButton = (Button) findViewById(R.id.mergeGroupsButton);
        liveTournamentButton = (Button) findViewById(R.id.liveTournamentButton);
        groupsButton = (Button) findViewById(R.id.groupsButton);
        allowPlayerEditsButton = (Button) findViewById(R.id.allowPlayerEditsButton);
        eventHubButton = (Button) findViewById(R.id.eventHubButton);
        messagesButton = (Button) findViewById(R.id.messagesButton);



        compareStatsButton = (Button) findViewById(R.id.compareStatsButton);
        rankingsButton = (Button) findViewById(R.id.ranking);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this,
                        StatisticsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        rankingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, RankingSelectionActivity.class);
                i.putExtra("selection", "highScore");
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, StatisticsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        compareStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, CompareStatisticsLookupActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        scoreKeepingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, GameTypeSelection.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, playerMessaging.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });


        editPlayerStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent i = new Intent(LoginMessageActivity.this, CoachChangeStatsLookupActivity.class);
                startActivity(i);
                */

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                String currDataPath = "coaches";
                final DatabaseReference coach = database.getReference(currDataPath);

                coach.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Check if the current user is a coach
                        if (dataSnapshot.hasChild(username)) {
                            Intent i = new Intent(LoginMessageActivity.this, CoachChangeStatsLookupActivity.class);
                            startActivity(i);
                        } else {

                            // Check if the current user is a player with edit privileges
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            String currDP = "users";
                            DatabaseReference userRef = database.getReference(currDP);
                            userRef = userRef.child(username);

                            userRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot db) {
                                    String canEdit = db.child("canEdit").getValue().toString();
                                    if (canEdit.equals("1")) {

                                        // If player has edit privileges, allow editing of own stats
                                        Intent i = new Intent(LoginMessageActivity.this, CoachChangeStatsActivity.class);
                                        i.putExtra("username", username);
                                        startActivity(i);
                                    } else {

                                        // If player does not have edit privileges, tell them
                                        Intent i = new Intent(LoginMessageActivity.this, NotCoachFailureActivity.class);
                                        i.putExtra("username", username);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError dbError) {
                                    System.out.println("Retrieving user edit stats failed: " + dbError.getCode());
                                }
                            });

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Retrieving coach status failed: " + databaseError.getCode());
                    }
                });
            }
        });
/*
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, createGroupActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        editGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, editGroupsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        groupStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, groupStatisticsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        mergeGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, mergeGroupActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        THIS HAS BEEN MOVED TO THE GROUPS HUB ACTIVITY
*/
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, groupHubActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        eventHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, EventHubActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        /*
        Checks if the current user has coach privileges
         */
        liveTournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                String currDataPath = "coaches";
                final DatabaseReference coach = database.getReference(currDataPath);

                coach.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username)) {
                            Intent i = new Intent(LoginMessageActivity.this, LiveTournamentActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(LoginMessageActivity.this, NotCoachFailureActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Retrieving coach status failed: " +
                                databaseError.getCode());
                    }
                });
            }
        });

        allowPlayerEditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                String currDataPath = "coaches";
                final DatabaseReference coach = database.getReference(currDataPath);

                coach.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username)) {
                            Intent i = new Intent(LoginMessageActivity.this, AllowPlayerEditsLookupActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(LoginMessageActivity.this, NotCoachFailureActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Retrieving coach status failed: " +
                                databaseError.getCode());
                    }
                });
            }
        });
    }
}