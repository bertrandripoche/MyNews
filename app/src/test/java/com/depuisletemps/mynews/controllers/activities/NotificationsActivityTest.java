package com.depuisletemps.mynews.controllers.activities;

import android.content.Context;
import android.content.res.Resources;
import android.widget.CheckBox;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class NotificationsActivityTest {
    @Mock
    Context mockApplicationContext;
    @Mock
    Resources mockContextResources;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       // when(mockApplicationContext.getResources()).thenReturn(mockContextResources);
       // when(mockContextResources.getString(anyInt())).thenReturn("mocked string");
       // when(mockApplicationContext.getString(anyInt())).thenReturn("mocked string");
      //  doReturn("mocked string").when(mockApplicationContext).getString(anyInt());
    }

    @Test
    public void havingAUserInputShouldReturnTrue() throws Exception {
        NotificationsActivity activity = new NotificationsActivity();

        assertTrue(activity.checkQueryTermValidity("Test", "Msg"));
    }

    @Test
    public void havingNoUserInputShouldReturnFalse() throws Exception {
        NotificationsActivity activity = mock(NotificationsActivity.class);
  //      doNothing().when(activity).disableNotificationSwitch("OK");

        assertFalse(activity.checkQueryTermValidity("", "Msg"));
    }

    @Test
    public void havingAtLeastOneCheckboxCheckedShouldReturnTrue() throws Exception {
        NotificationsActivity activity = new NotificationsActivity();

        List<CheckBox> checkBoxes;
        CheckBox cb1 = mock(CheckBox.class);
        CheckBox cb2 = mock(CheckBox.class);
        CheckBox cb3 = mock(CheckBox.class);
        checkBoxes = Arrays.asList(cb1,cb2,cb3);

        when(cb1.isChecked()).thenReturn(true);

        assertTrue(activity.checkCategoriesValidity(checkBoxes, "Msg"));
    }

    @Test
    public void havingNoCheckboxCheckedShouldReturnFalse() throws Exception {
        NotificationsActivity activity = mock(NotificationsActivity.class);

     //   doNothing().when(activity).disableNotificationSwitch("OK");

        List<CheckBox> checkBoxes;
        CheckBox cb1 = mock(CheckBox.class);
        CheckBox cb2 = mock(CheckBox.class);
        CheckBox cb3 = mock(CheckBox.class);
        checkBoxes = Arrays.asList(cb1,cb2,cb3);

        assertFalse(activity.checkCategoriesValidity(checkBoxes, "Msg"));
    }

}