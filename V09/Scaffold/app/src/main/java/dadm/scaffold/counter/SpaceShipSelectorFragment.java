package dadm.scaffold.counter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;

public class SpaceShipSelectorFragment extends BaseFragment implements View.OnClickListener {

    ImageView ship_1, ship_2, ship_3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spaceship_selector, container, false);
        return rootView;
    }

    private void startGame(int ship_selected){
        Context context =  ((ScaffoldActivity)getActivity()).getApplicationContext();
        int identi = context.getResources().getIdentifier("ship_"+ship_selected,"drawable",context.getPackageName());
        ((ScaffoldActivity)getActivity()).startGame(identi);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ship_1 = (ImageView) view.findViewById(R.id.img_1);
        ship_2 = (ImageView) view.findViewById(R.id.img_2);
        ship_3 = (ImageView) view.findViewById(R.id.img_3);


        ship_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startGame(1);
                return false;
            }
        });

        ship_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startGame(2);
                return false;
            }
        });

        ship_3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startGame(3);
                return false;
            }
        });



        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onBackPressed() {

        return false; //true para que no le de pa atras desde el menu.
    }


    @Override
    public void onClick(View v) {

    }
}
