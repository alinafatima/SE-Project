package com.example.alina.seprojecttracker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity implements OnClickListener
{
    Button signin;
    Button signupEmployer;
    Button signupEmployee;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Alina", "OnCreate was called.");
        signin=(Button)findViewById(R.id.signin);
        signupEmployer=(Button)findViewById(R.id.signupEmployer);
        signupEmployee=(Button)findViewById(R.id.signupEmployee);
        signin.setOnClickListener(this);
        signupEmployer.setOnClickListener(this);
        signupEmployee.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences("SEProjectTracker", MODE_PRIVATE);
        String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        String email = prefs.getString("identifierString", "no email");
        Log.d("Alina", name);


    }

    public void onClick(View v)
    {
        if(v.getId()==R.id.signin)
        {
            Log.d("Alina", "Login Button was clicked.");
            Intent i=new Intent(this, login.class);
            startActivity(i);

        }
        else if(v.getId()==R.id.signupEmployer)
        {
            Log.d("Alina", "SignUp1 button was clicked.");
            Intent i=new Intent(this, SignUpEmployer.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.signupEmployee)
        {
            Log.d("Alina", "SignUp2 button was clicked.");
            Intent i=new Intent(this, SignUpEmployee.class);
            startActivity(i);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("Alina","OnResume was called.");
        //App will crash on removing this statement
    }
    @Override
    protected void onStart()
    {
        super.onStart();//App will crash on removing this statement
        Log.d("Alina","OnStart was called.");
    }
    @Override
    protected void onPause()
    {
        super.onPause();//App will crash on removing this statement
        Log.d("Alina","OnPause was called.");
    }
    @Override
    protected void onStop()
    {
        super.onStop();//App will crash on removing this statement
        Log.d("Alina","OnStop was called.");
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();//App will crash on removing this statement
        Log.d("Alina","OnRestart was called.");
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();//App will crash on removing this statement
        Log.d("Alina","OnDestroy was called.");
    }


}
