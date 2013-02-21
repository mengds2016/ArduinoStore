package no.group09.utils;

/*
* Licensed to UbiCollab.org under one or more contributor
* license agreements.  See the NOTICE file distributed 
* with this work for additional information regarding
* copyright ownership. UbiCollab.org licenses this file
* to you under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

import no.group09.arduinoair.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;

public class Preferences extends PreferenceActivity {

	TextView l;


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*This method is deprecated, but have to be used when developing for
		 * Android 3.0 or lower.
		 */
		addPreferencesFromResource(R.xml.preferences);
		
//	
//		setContentView(R.layout.preferences);
//		
//		l = (TextView)findViewById(R.id.pin_tv);
//		
	}
}