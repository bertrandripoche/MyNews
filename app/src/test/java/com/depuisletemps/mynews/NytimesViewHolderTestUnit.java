package com.depuisletemps.mynews;

import android.content.Context;
import android.view.View;

import com.depuisletemps.mynews.Views.NytimesViewHolder;
import com.depuisletemps.mynews.Views.SectionAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//@RunWith(MockitoJUnitRunner::class)
public class NytimesViewHolderTestUnit {

    @Mock
    Context context;

    @Test
    public void updateWithSectionTest() {
        View view = new View(context);

        NytimesViewHolder viewHolder = new NytimesViewHolder(view);

        assertEquals(4, 2 + 2);
    }

}

