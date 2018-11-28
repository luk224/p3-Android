package dadm.scaffold.counter;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.FramesPerSecondCounter;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameView;
import dadm.scaffold.engine.PlayerHealthUI;
import dadm.scaffold.engine.PlayerScoreUI;
import dadm.scaffold.input.JoystickInputController;
import dadm.scaffold.space.GameController;
import dadm.scaffold.space.ParallaxBackground;
import dadm.scaffold.space.SpaceShipPlayer;


public class GameFragment extends BaseFragment implements View.OnClickListener {
    private GameEngine theGameEngine;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final int ship_selected = getArguments().getInt("SHIP_SELECTED");
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                theGameEngine.setTheInputController(new JoystickInputController(getView()));
                theGameEngine.addGameObject(new ParallaxBackground(theGameEngine,100d   ,R.drawable.fondo0));//https://www.artstation.com/artwork/LWYvk
                theGameEngine.addGameObject(new ParallaxBackground(theGameEngine,220d   ,R.drawable.fish_bg_transparent));//https://www.artstation.com/artwork/LWYvk

                SpaceShipPlayer ssp = new SpaceShipPlayer(theGameEngine , ship_selected);
                theGameEngine.addGameObject(new GameController(theGameEngine,ssp));
                theGameEngine.addGameObject(new ParallaxBackground(theGameEngine,160d    ,R.drawable.water1));
                theGameEngine.addGameObject(ssp);
                theGameEngine.addGameObject(new PlayerHealthUI(theGameEngine,ssp));
                theGameEngine.addGameObject(new PlayerScoreUI(theGameEngine,ssp));
                theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));
                theGameEngine.startGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        int c = fm.getBackStackEntryCount();
                        //Se borra toda la pila para volver al menu principal.
                        for(int i = 0; i < c; ++i) {
                            fm.popBackStack();
                        }
                        //((ScaffoldActivity)getActivity()).navigateBack();


                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        theGameEngine.resumeGame();
                    }
                })
                .create()
                .show();

    }

    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else {
            theGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }
}
