package com.depuisletemps.mynews.controllers.activities;

import android.widget.EditText;

import com.depuisletemps.mynews.R;

import org.junit.Before;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)

public class NotificationsActivityTest {

    @Test
    public void thisAlwaysPasses() {
        assertTrue(true);
    }

    @Test
    public void havingAUserInputShouldReturnTrue() throws Exception {
        //NotificationsActivity mNotificationsActivity = new NotificationsActivity();
        //EditText queryTerms = mock(EditText.class);
        NotificationsActivity activity = new NotificationsActivity();
        EditText queryTerms = mock(EditText.class);
        queryTerms = activity.findViewById(R.id.activity_notif_input);

        activity.setQueryTerms(queryTerms);
        queryTerms.setText("Trump");

        //when(queryTerms.getText().toString()).thenReturn("Trump");

        assertTrue(activity.checkQueryTermValidity());
    }

}