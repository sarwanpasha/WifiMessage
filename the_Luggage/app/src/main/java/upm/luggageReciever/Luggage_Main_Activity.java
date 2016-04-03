package upm.luggageReciever;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import upm.userinterfaces.R;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

//import com.yalantis.euclid.library.EuclidActivity;
//import com.yalantis.euclid.library.EuclidListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import upm.userinterfaces.R;

public class Luggage_Main_Activity extends EuclidActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_luggage__main_);
//    }




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mButtonProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Luggage_Main_Activity.this, "Oh hi!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        protected BaseAdapter getAdapter() {
            Map<String, Object> profileMap;
            List<Map<String, Object>> profilesList = new ArrayList<>();

            int[] avatars = {
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1,
                    R.drawable.s1};
            String[] names = getResources().getStringArray(R.array.array_names);

            for (int i = 0; i < avatars.length; i++) {
                profileMap = new HashMap<>();
                profileMap.put(EuclidListAdapter.KEY_AVATAR, avatars[i]);
                profileMap.put(EuclidListAdapter.KEY_NAME, names[i]);
                profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_SHORT, getString(R.string.lorem_ipsum_short));
                profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_FULL, getString(R.string.lorem_ipsum_long));
                profilesList.add(profileMap);
            }

            return new EuclidListAdapter(this, R.layout.list_item, profilesList);
        }

    }
