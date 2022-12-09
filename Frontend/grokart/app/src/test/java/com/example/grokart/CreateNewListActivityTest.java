package com.example.grokart;

import android.support.test.runner.AndroidJUnit4;

import androidx.test.core.app.ActivityScenario;

import com.example.grokart.utils.KartItemModel;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class CreateNewListActivityTest {
    private ArrayList<KartItemModel> storeItems;
    private lateinit var scenario: ActivityScenario<CreateNewListActivity>;

    @Test
    public void testOnCreate() {
       onView()
    }

    @Test
    public void testGetStoreItems() {
    }

    @Test
    public void testFillUserKart() {
    }

    @Test
    public void testGetItemInfo() {
    }

    @Test
    public void testPopulateRows() {
    }
}