package com.depuisletemps.mynews.controllers.activities;

import android.widget.CheckBox;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class NotificationsActivityTest {

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

        List<CheckBox> checkBoxes;
        CheckBox cb1 = mock(CheckBox.class);
        CheckBox cb2 = mock(CheckBox.class);
        CheckBox cb3 = mock(CheckBox.class);
        checkBoxes = Arrays.asList(cb1,cb2,cb3);

        assertFalse(activity.checkCategoriesValidity(checkBoxes, "Msg"));
    }

}