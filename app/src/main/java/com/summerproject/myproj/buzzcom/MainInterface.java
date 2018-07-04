package com.summerproject.myproj.buzzcom;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;


// OnItemSelectedListener is a custom interface that enables communication between Fragment and
// its Activity (see bottom of the code for more information)
public class MainInterface extends AppCompatActivity implements android.support.v7.app.ActionBar.TabListener,MyFeed.OnFragmentInteractionListener,PopularFeed.OnFragmentInteractionListener,FunFeed.OnFragmentInteractionListener
{


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    enum TabType
    {
        SEARCH, LIST, FAVORITES
    }

    // Tab back stacks
    private HashMap<TabType, Stack<String>> backStacks;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Initialize ActionBar
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set back stacks
        if (savedInstanceState != null)
        {
            // Read back stacks after orientation change
            backStacks = (HashMap<TabType, Stack<String>>) savedInstanceState.getSerializable("stacks");
        }
        else
        {
            // Initialize back stacks on first run
            backStacks = new HashMap<TabType, Stack<String>>();
            backStacks.put(TabType.SEARCH, new Stack<String>());
            backStacks.put(TabType.LIST, new Stack<String>());
            backStacks.put(TabType.FAVORITES, new Stack<String>());
        }

        // Create tabs
        bar.addTab(bar.newTab().setTag(TabType.SEARCH).setText("My Feed").setTabListener(this));
        bar.addTab(bar.newTab().setTag(TabType.LIST).setText("Popular").setTabListener(this));
        bar.addTab(bar.newTab().setTag(TabType.FAVORITES).setText("Fun Feed").setTabListener(this));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // Select proper stack
        ActionBar.Tab tab = getSupportActionBar().getSelectedTab();
        Stack<String> backStack = backStacks.get(tab.getTag());
        if (! backStack.isEmpty())
        {
            // Restore topmost fragment (e.g. after application switch)
            String tag = backStack.peek();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment.isDetached())
            {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.attach(fragment);
                ft.commit();
            }
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // Select proper stack
        ActionBar.Tab tab = getSupportActionBar().getSelectedTab();
        Stack<String> backStack = backStacks.get(tab.getTag());
        if (! backStack.isEmpty())
        {
            // Detach topmost fragment otherwise it will not be correctly displayed
            // after orientation change
            String tag = backStack.peek();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            ft.detach(fragment);
            ft.commit();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore selected tab
        int saved = savedInstanceState.getInt("tab", 0);
        if (saved != getSupportActionBar().getSelectedNavigationIndex())
            getSupportActionBar().setSelectedNavigationItem(saved);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        // Save selected tab and all back stacks
        outState.putInt("tab", getSupportActionBar().getSelectedNavigationIndex());
        outState.putSerializable("stacks", backStacks);
    }

    @Override
    public void onBackPressed()
    {
        // Select proper stack
        ActionBar.Tab tab = getSupportActionBar().getSelectedTab();
        Stack<String> backStack = backStacks.get(tab.getTag());
        String tag = backStack.pop();
        if (backStack.isEmpty())
        {
            // Let application finish
            super.onBackPressed();
        }
        else
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            // Animate return to previous fragment
            // Remove topmost fragment from back stack and forget it
            ft.remove(fragment);
            showFragment(backStack, ft);
            ft.commit();
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
    {
        // Select proper stack
        Stack<String> backStack = backStacks.get(tab.getTag());
        if (backStack.isEmpty())
        {
            // If it is empty instantiate and add initial tab fragment
            Fragment fragment;
            switch ((TabType) tab.getTag())
            {
                case SEARCH:
                    fragment = Fragment.instantiate(this, MyFeed.class.getName());
                    break;
                case LIST:
                    fragment = Fragment.instantiate(this, PopularFeed.class.getName());
                    break;
                case FAVORITES:
                    fragment = Fragment.instantiate(this, FunFeed.class.getName());
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown tab");
            }
            addFragment(fragment, backStack, ft);
        }
        else
        {
            // Show topmost fragment
            showFragment(backStack, ft);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
    {
        // Select proper stack
        Stack<String> backStack = backStacks.get(tab.getTag());
        // Get topmost fragment
        String tag = backStack.peek();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        // Detach it
        ft.detach(fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
    {
        // Select proper stack
        Stack<String> backStack = backStacks.get(tab.getTag());

        if (backStack.size() > 1)

        // Clean the stack leaving only initial fragment
        while (backStack.size() > 1)
        {
            // Pop topmost fragment
            String tag = backStack.pop();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            // Remove it
            ft.remove(fragment);
        }
        showFragment(backStack, ft);
    }

    private void addFragment(Fragment fragment)
    {
        // Select proper stack
        ActionBar.Tab tab = getSupportActionBar().getSelectedTab();
        Stack<String> backStack = backStacks.get(tab.getTag());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Animate transfer to new fragment

        // Get topmost fragment
        String tag = backStack.peek();
        Fragment top = getSupportFragmentManager().findFragmentByTag(tag);
        ft.detach(top);
        // Add new fragment
        addFragment(fragment, backStack, ft);
        ft.commit();
    }

    private void addFragment(Fragment fragment, Stack<String> backStack, FragmentTransaction ft)
    {
        // Add fragment to back stack with unique tag
        String tag = UUID.randomUUID().toString();
        ft.add(android.R.id.content, fragment, tag);
        backStack.push(tag);
    }

    private void showFragment(Stack<String> backStack, FragmentTransaction ft)
    {
        // Peek topmost fragment from the stack
        String tag = backStack.peek();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        // and attach it
        ft.attach(fragment);
    }

    // The following code shows how to properly open new fragment. It assumes
    // that parent fragment calls its activity via interface. This approach
    // is described in Android development guidelines.
    /*@Override
    public void onItemSelected(String item)
    {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString("item", item);
        fragment.setArguments(args);
        addFragment(fragment);
    }*/
}
