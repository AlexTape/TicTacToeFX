package de.alextape.tictactoe;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Class Control.
 */
public class Control extends Application {

    /** The gui. */
    private static Gui gui;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /*
     * (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public final void start(final Stage primaryStage) {

        final int i1 = 1;
        final int i4 = 4;

        // init
        gui = Gui.getInstance();
        gui.initModel();

        // relate
        gui.setPrimaryStage(primaryStage);
        gui.setTitle("TicTacToe");
        gui.setPlayer1("John");
        gui.setPlayer2("Frank");
        gui.setPlayer1color(gui.getPlayerColors()[i1]);
        gui.setPlayer2color(gui.getPlayerColors()[i4]);

        // render
        gui.renderPlayfield();

        // release
        gui.release();

    }

    /**
     * Gets the gui.
     *
     * @return the gui
     */
    public static Gui getGui() {
        return gui;
    }

    /**
     * Sets the gui.
     *
     * @param newGui the new gui
     */
    public static void setGui(final Gui newGui) {
        Control.gui = newGui;
    }

}
