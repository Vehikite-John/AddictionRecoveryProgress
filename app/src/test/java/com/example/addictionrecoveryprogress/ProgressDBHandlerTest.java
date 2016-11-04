package com.example.addictionrecoveryprogress;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProgressDBHandlerTest {

  @Mock
  Context mContext;

  @Mock
  SQLiteDatabase mDb;

  @Test
  public void testOnCreate() throws Exception {
    ProgressDBHandler handler = new ProgressDBHandler(mContext);

    handler.onCreate(mDb);
    // creates 2 tables
    verify(mDb, times(2)).execSQL(startsWith("CREATE TABLE"));
  }

  @Test
  public void testOnUpgrade() throws Exception {
    ProgressDBHandler handler = new ProgressDBHandler(mContext);
    handler.onUpgrade(mDb, 1, 2);
    // creates 2 tables
    verify(mDb, times(2)).execSQL(startsWith("CREATE TABLE"));
  }
}
