package dadm.scaffold.counter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;


public class EndOfGameFragment  extends BaseFragment implements View.OnClickListener  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_end_of_game, container, false);



        return rootView;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if(getArguments().getBoolean("WIN")){
            ((TextView) view.findViewById(R.id.text_win)).setText(R.string.youwon);
        }else{
            ((TextView) view.findViewById(R.id.text_win)).setText(R.string.youlose);
        }
        ((TextView) view.findViewById(R.id.text_score)).setText( getString(R.string.score)+" "+Integer.toString(getArguments().getInt("SCORE")));
        int secs = (getArguments().getInt("SECONDS"));
        int min = secs/60;
        secs%=60;
        if(secs<10){
            ((TextView) view.findViewById(R.id.text_time)).setText( getString(R.string.time )+" "+ min+":0"+secs);
        }else{

            ((TextView) view.findViewById(R.id.text_time)).setText(getString(R.string.time)+" "+min+":"+secs);
        }


        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_main_menu).setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        int c = fm.getBackStackEntryCount();
        //Se borra toda la pila para volver al menu principal.
        for(int i = 0; i < c; ++i) {
            fm.popBackStack();
        }
        return true; //true para que no le de pa atras desde el menu.
    }


    @Override
    public void onClick(View v) {

        onBackPressed();

    }
}
