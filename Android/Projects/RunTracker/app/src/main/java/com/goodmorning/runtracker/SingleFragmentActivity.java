package com.goodmorning.runtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/*******************************************************************
 * Desc: This is an abstract class to be extended by activities
 *       launching single (only 1) fragment. This class inflates the
 *       single frame layout which contains a container which will
 *       host the fragment. It calls the "fragment manager" which will
 *       help to add the fragment to the container of the layout.
 *       Subclasses of SingleFragmentActivity will implement
 *       createFragment() method to return an instance of the fragment
 *       that the activity will host.
 * Developer: Mohan Prabhu
 *******************************************************************/
public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_layout);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            // Subclasses of SingleFragmentActivity will implement createFragment() method to return
            // an instance of the fragment that the activity is hosting.
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
