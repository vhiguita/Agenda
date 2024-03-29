/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.demo.contacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ContactsEdit extends Activity {

	private EditText mTitleText;
	// private EditText mBodyText;
	private Long mRowId;
	private ContactsDbAdapter mDbHelper;

	private CheckBox mCheckBoxFem;
	private CheckBox mCheckBoxMasc;
	private CheckBox mCheckBoxCine;
	private CheckBox mCheckBoxTelevision;
	private CheckBox mCheckBoxDeporte;
	private CheckBox mCheckBoxAmistad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new ContactsDbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.contacts_edit);

		mTitleText = (EditText) findViewById(R.id.title);
		mCheckBoxMasc = (CheckBox) findViewById(R.id.CheckBoxMasc);
		mCheckBoxFem = (CheckBox) findViewById(R.id.CheckBoxFem);
		mCheckBoxCine = (CheckBox) findViewById(R.id.Cine);
		mCheckBoxTelevision = (CheckBox) findViewById(R.id.Television);
		mCheckBoxDeporte = (CheckBox) findViewById(R.id.Deporte);
		mCheckBoxAmistad = (CheckBox) findViewById(R.id.Amistad);
		Button confirmButton = (Button) findViewById(R.id.confirm);

		mRowId = savedInstanceState != null ? savedInstanceState
				.getLong(ContactsDbAdapter.KEY_ROWID) : null;
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(ContactsDbAdapter.KEY_ROWID)
					: null;
		}

		populateFields();

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}

		});

		OnCheckedChangeListener checkedListener = new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// mCheckBoxMasc.setChecked(!isChecked);
				if (buttonView == mCheckBoxMasc) {
					mCheckBoxFem.setChecked(!isChecked);
				} else if (buttonView == mCheckBoxFem) {
					mCheckBoxMasc.setChecked(!isChecked);
				}

			}
		};
		mCheckBoxMasc.setOnCheckedChangeListener(checkedListener);
		mCheckBoxFem.setOnCheckedChangeListener(checkedListener);

	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor note = mDbHelper.fetchNote(mRowId);
			startManagingCursor(note);
			mTitleText.setText(note.getString(note
					.getColumnIndexOrThrow(ContactsDbAdapter.KEY_TITLE)));

			boolean isSexMas = note.getString(
					note.getColumnIndexOrThrow(ContactsDbAdapter.KEY_BODY))
					.equals("Masculino");
			mCheckBoxMasc.setChecked(isSexMas);
			mCheckBoxFem.setChecked(!isSexMas);
			boolean isCine = note.getString(
					note.getColumnIndexOrThrow(ContactsDbAdapter.KEY_CINE))
					.equals("Cine");
			mCheckBoxCine.setChecked(isCine);
			boolean isTelevision = note.getString(
					note.getColumnIndexOrThrow(ContactsDbAdapter.KEY_TELEVISION))
					.equals("Television");
			mCheckBoxTelevision.setChecked(isTelevision);
			boolean isDeporte = note.getString(
					note.getColumnIndexOrThrow(ContactsDbAdapter.KEY_DEPORTE))
					.equals("Deporte");
			mCheckBoxDeporte.setChecked(isDeporte);
			boolean isAmistad = note.getString(
					note.getColumnIndexOrThrow(ContactsDbAdapter.KEY_AMISTAD))
					.equals("Amistad");
			mCheckBoxAmistad.setChecked(isAmistad);

			// mBodyText.setText(note.getString(
			// note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(ContactsDbAdapter.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	private void saveState() {
		String title = mTitleText.getText().toString();
		// String body = mBodyText.getText().toString();
		String sexFem = mCheckBoxFem.isChecked() ? "Femenino" : "Masculino";
		String cine = mCheckBoxCine.isChecked() ? "Cine" : " ";
		String tel = mCheckBoxTelevision.isChecked() ? "Television" : " ";
		String deporte = mCheckBoxDeporte.isChecked() ? "Deporte" : " ";
		String amistad = mCheckBoxAmistad.isChecked() ? "Amistad" : " ";

		if (mRowId == null) {
			long id = mDbHelper.createContact(title, sexFem, cine, tel,
					deporte, amistad);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateNote(mRowId, title, sexFem, cine, tel, deporte,
					amistad);
		}
	}

}
