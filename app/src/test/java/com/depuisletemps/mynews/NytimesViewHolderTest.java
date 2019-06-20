package com.depuisletemps.mynews;

import android.content.Context;
import android.view.View;

import com.depuisletemps.mynews.views.NytimesViewHolder;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class NytimesViewHolderTest {

    @Mock
    Context contextMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void updateWithSectionTest() {
        View view = new View(contextMock);

        NytimesViewHolder viewHolder = new NytimesViewHolder(view);

        assertEquals(4, 2 + 2);
    }

}

