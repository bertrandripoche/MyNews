package com.depuisletemps.mynews.controllers.fragments;

import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SearchResultFragmentTest {

    @Test
    public void havingCheckBoxCheckedShouldReturnValidFilterString() throws Exception {
        SearchResultFragment searchResultFragment = new SearchResultFragment();

        Bundle extras = mock(Bundle.class);
        doReturn("unchecked").when(extras).getString("Arts");
        doReturn("unchecked").when(extras).getString("Books");
        doReturn("unchecked").when(extras).getString("Science");
        doReturn("unchecked").when(extras).getString("Sports");
        doReturn("checked").when(extras).getString("World");
        doReturn("checked").when(extras).getString("Technology");

        assertEquals("section_name:(Technology\" \"World)",searchResultFragment.buildFilterQuery(extras));
    }

    @Test
    public void havingCheckBoxCheckedShouldReturnANullFilterString() throws Exception {
        SearchResultFragment searchResultFragment = new SearchResultFragment();

        Bundle extras = mock(Bundle.class);
        doReturn("unchecked").when(extras).getString("Arts");
        doReturn("unchecked").when(extras).getString("Books");
        doReturn("unchecked").when(extras).getString("Science");
        doReturn("unchecked").when(extras).getString("Sports");
        doReturn("unchecked").when(extras).getString("World");
        doReturn("unchecked").when(extras).getString("Technology");

        assertEquals("",searchResultFragment.buildFilterQuery(extras));
    }

}