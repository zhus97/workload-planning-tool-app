package com.example.workloadplanningtool.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.workloadplanningtool.Fragments.tabAllAssignments;
import com.example.workloadplanningtool.Fragments.tabDueDate;
import com.example.workloadplanningtool.Fragments.tabPriority;
import com.example.workloadplanningtool.Fragments.tabType;


public class PageAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }


    //Gets position of tab selected and displays fragment associated with that position
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new tabAllAssignments();
            case 1:
                return new tabPriority();
            case 2:
                return new tabType();
            case 3:
                return new tabDueDate();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

