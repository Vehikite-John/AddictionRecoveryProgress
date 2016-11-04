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

    // This test is bogus because the upgrade contract is to preserve data when
    // changing the database schema, not to create tables. A better test would be
    // to save something in the database in the old format, upgrade, then retrieve that
    // record and confirm it is correct. Not sure how to do all of that in a unit test.
    handler.onUpgrade(mDb, 1, 2);
    // creates 2 tables
    verify(mDb, times(2)).execSQL(startsWith("CREATE TABLE"));
  }
}
