package com.depuisletemps.mynews.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DateToolsTest {

    @Test(expected= ParseException.class)
    public void havingAnIncorrectStringShouldReturnAnException() throws Exception {
        DateTools.getDateStringFromString("wrong string");
    }

    @Test
    public void havingACorrectDateStringShouldReturnACorrectFormattedDate() throws Exception {
        assertEquals("20190630", DateTools.getDateStringFromString("30/6/2019"));
    }

    @Test
    public void havingAGoodDateStringShouldReturnADateObject() throws Exception{
        DateTools.getDate("30/6/2019");
    }

    @Test (expected= ParseException.class)
    public void havingAWrongStringShouldReturnAnException() throws Exception{
        DateTools.getDate("wrong string");
    }

    @Test (expected= ParseException.class)
    public void havingAWrongDateStringShouldReturnAnException() throws Exception{
        DateTools.getDate("20190630");
    }
}