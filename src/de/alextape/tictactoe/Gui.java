package de.alextape.tictactoe;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The Class Gui.
 */
public class Gui {

    /** The instance. */
    private static Gui instance = null;

    /** The primary stage. */
    private static Stage primaryStage;

    /**
     * Gets the single instance of Gui.
     *
     * @return single instance of Gui
     */
    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    /**
     * Renew.
     *
     * @return the gui
     */
    public static Gui renew() {
        instance = null;
        instance = new Gui();
        return instance;
    }

    /**
     * Sets the instance.
     *
     * @param newInstance the new instance
     */
    public static void setInstance(final Gui newInstance) {
        Gui.instance = newInstance;
    }

    /** The button field. */
    private GridPane buttonField;

    /** The buttons. */
    private Button[][] buttons;

    /** The grid. */
    private GridPane grid;

    /** The headline. */
    private Button headline;

    /** The model. */
    private Model model;

    /** The root. */
    private VBox root;

    /** The scene. */
    private Scene scene;

    /** The time. */
    private Button time;

    /** The time counter. */
    private int timeCounter;

    /** The time grid. */
    private GridPane timeGrid;

    /**
     * Instantiates a new gui.
     */
    public Gui() {

        // grid
        this.grid = new GridPane();

        this.time = new Button();
        this.timeCounter = 0;
        this.time.setText("Time: " + String.valueOf(this.timeCounter));

        final int i15 = 15;
        final int i0 = 0;
        final int i10 = 10;

        // generate Grid
        this.timeGrid = new GridPane();
        this.timeGrid.setPadding(new Insets(i15, i0, i0, i10));
        this.timeGrid.add(time, 0, 0);

        // timer
        Animation countAnimation = buildCounter();
        SequentialTransitionBuilder builder = SequentialTransitionBuilder
                .create();
        builder.children(countAnimation);
        builder.cycleCount(Integer.MAX_VALUE);
        SequentialTransition sq = builder.build();

        // play transition
        sq.play();

    }

    /**
     * Builds the counter.
     *
     * @return the pause transition
     */
    private PauseTransition buildCounter() {
        PauseTransition counter = new PauseTransition(Duration.seconds(1));
        counter.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent arg0) {
                timeCounter++;
                time.setText("Time: " + String.valueOf(timeCounter));
            }
        });
        return counter;
    }

    /**
     * Gets the button feld.
     *
     * @return the button feld
     */
    public final GridPane getButtonFeld() {
        return buttonField;
    }

    /**
     * Gets the button field.
     *
     * @return the button field
     */
    public final GridPane getButtonField() {
        return buttonField;
    }

    /**
     * Gets the buttons.
     *
     * @return the buttons
     */
    public final Button[][] getButtons() {
        return buttons;
    }

    /**
     * Gets the game board.
     *
     * @return the game board
     */
    private GridPane getGameBoard() {

        final EventHandler<MouseEvent> myMouseHandler =
                new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent event) {

                // get active element
                Object source = event.getSource();

                if (source instanceof Button) {

                    Button clickedButton = (Button) source;

                    if (clickedButton.getText().compareTo("X") == -1) {

                        // set X
                        clickedButton.setText("X");

                        // set color
                        if (getMoveCounter() % 2 == 0) {
                            clickedButton.setStyle("-fx-base: "
                                    + getPlayer2color() + ";");
                            clickedButton.setId(getPlayer2color());
                        } else {
                            clickedButton.setStyle("-fx-base: "
                                    + getPlayer1color() + ";");
                            clickedButton.setId(model.getPlayer1color());
                        }

                        // movecount
                        refreshMoveCounter();

                    }

                    // determine situation
                    boolean[] result = model.isWin(getMatrixString());
                    if (result[0]) {
                        // player1 win
                        winPopup(getPlayer1() + " wins!");
                    }
                    if (result[1]) {
                        // player2 win
                        winPopup(getPlayer2() + " wins!");
                    }

                    if (model.isDraw(getMatrixString())) {
                        winPopup("Draw!");
                    }

                }
            }
        };

        final int i10 = 10;

        // generate grid
        buttonField = new GridPane();
        buttonField.setHgap(i10);
        buttonField.setVgap(i10);
        buttonField.setPadding(new Insets(i10, i10, i10, i10));

        final int i3 = 3;
        final int i50 = 50;

        // instance buttons
        buttons = new Button[i3][i3];
        for (int i = 0; i < buttons.length; i++) {

            for (int z = 0; z < buttons[i].length; z++) {

                buttons[i][z] = new Button();
                buttons[i][z].setId("NULL");
                buttons[i][z].setMinSize(i50, i50);
                buttons[i][z].setMaxSize(i50, i50);

                // relate listener
                buttons[i][z].setOnMouseClicked(myMouseHandler);

                // add to pane
                buttonField.add(buttons[i][z], i, z);

            }
        }

        // return field
        return buttonField;

    }

    /**
     * Gets the grid.
     *
     * @return the grid
     */
    public final GridPane getGrid() {
        return grid;
    }

    /**
     * Gets the headline.
     *
     * @return the headline
     */
    public final Button getHeadline() {
        return headline;
    }

    /**
     * Gets the matrix string.
     *
     * @return the matrix string
     */
    public final String getMatrixString() {

        StringBuilder result = new StringBuilder();

        int elementCounter = 0;

        for (int i = 0; i < buttons.length; i++) {

            for (int z = 0; z < buttons[i].length; z++) {

                elementCounter++;

            }

        }

        int[] matrix = new int[elementCounter];
        int index = 0;

        for (int i = 0; i < buttons.length; i++) {

            for (int z = 0; z < buttons[i].length; z++) {

                Button button = buttons[i][z];

                if (button.getId().equalsIgnoreCase("NULL")) {
                    matrix[index] = 0;
                } else {
                    if (button.getId().equalsIgnoreCase(getPlayer1color())) {
                        matrix[index] = 1;
                    }
                    if (button.getId().equalsIgnoreCase(getPlayer2color())) {
                        matrix[index] = 2;
                    }
                }

                index++;

            }

        }

        for (int value : matrix) {
            result.append(value);
        }

        return result.toString();
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public final Model getModel() {
        return model;
    }

    /**
     * Gets the move counter.
     *
     * @return the move counter
     */
    public final int getMoveCounter() {
        System.out.println(this.getModel().getMoveCounter());
        return this.getModel().getMoveCounter();
    }

    /**
     * Gets the player1.
     *
     * @return the player1
     */
    public final String getPlayer1() {
        return this.getModel().getPlayer1();
    }

    /**
     * Gets the player1color.
     *
     * @return the player1color
     */
    public final String getPlayer1color() {
        return this.getModel().getPlayer1color();
    }

    /**
     * Gets the player2.
     *
     * @return the player2
     */
    public final String getPlayer2() {
        return this.getModel().getPlayer2();
    }

    /**
     * Gets the player2color.
     *
     * @return the player2color
     */
    public final String getPlayer2color() {
        return this.getModel().getPlayer2color();
    }

    /**
     * Gets the player colors.
     *
     * @return the player colors
     */
    public final String[] getPlayerColors() {
        return this.getModel().getPlayerColors();
    }

    /**
     * Gets the primary stage.
     *
     * @return the primary stage
     */
    public final Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Gets the root.
     *
     * @return the root
     */
    public final VBox getRoot() {
        return root;
    }

    /**
     * Gets the scene.
     *
     * @return the scene
     */
    public final Scene getScene() {
        return scene;
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public final Button getTime() {
        return time;
    }

    /**
     * Gets the time counter.
     *
     * @return the time counter
     */
    public final int getTimeCounter() {
        return timeCounter;
    }

    /**
     * Gets the time grid.
     *
     * @return the time grid
     */
    public final GridPane getTimeGrid() {
        return timeGrid;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public final String getTitle() {
        return this.model.getTitle();
    }

    /**
     * Inits the model.
     */
    public final void initModel() {
        // init model
        this.model = new Model();
        // move counter
        model.setMoveCounter(0);

    }

    /**
     * Refresh move counter.
     */
    public final void refreshMoveCounter() {
        this.getModel().setMoveCounter(this.getModel().getMoveCounter() + 1);
    }

    /**
     * Release.
     */
    public final void release() {
        // release
        primaryStage.setScene(this.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Render playfield.
     */
    public final void renderPlayfield() {

        // e.g.
        refreshMoveCounter();

        // introduce appname
        Gui.primaryStage.setTitle(this.model.getTitle());
        this.headline = setText(this.model.getTitle());

        final int i1 = 1;
        final int i2 = 2;
        final int i3 = 3;

        // generate view
        grid.add(timeGrid, i3, i1);
        grid.add(headline, i2, i1);
        grid.add(setText("Player1:" + getPlayer1() + "\n" + "Color:"
                + getPlayer1color()), 1, 2);
        grid.add(getGameBoard(), 2, 2);
        grid.add(setText("Player2:" + getPlayer2() + "\n" + "Color:"
                + getPlayer2color()), i3, i2);
        this.root = new VBox();
        root.getChildren().addAll(grid);

        // generate scene
        this.scene = new Scene(root);
    }

    /**
     * Sets the button field.
     *
     * @param newButtonField the new button field
     */
    public final void setButtonField(final GridPane newButtonField) {
        this.buttonField = newButtonField;
    }

    /**
     * Sets the buttons.
     *
     * @param newButtons the new buttons
     */
    public final void setButtons(final Button[][] newButtons) {
        this.buttons = newButtons;
    }

    /**
     * Sets the grid.
     *
     * @param newGrid the new grid
     */
    public final void setGrid(final GridPane newGrid) {
        this.grid = newGrid;
    }

    /**
     * Sets the headline.
     *
     * @param newHeadline the new headline
     */
    public final void setHeadline(final Button newHeadline) {
        this.headline = newHeadline;
    }

    /**
     * Sets the model.
     *
     * @param newModel the new model
     */
    public final void setModel(final Model newModel) {
        this.model = newModel;
    }

    /**
     * Sets the player1.
     *
     * @param player1 the new player1
     */
    public final void setPlayer1(final String player1) {
        this.getModel().setPlayer1(player1);
    }

    /**
     * Sets the player1color.
     *
     * @param player1color the new player1color
     */
    public final void setPlayer1color(final String player1color) {
        System.out.println("PLAYER1set: " + player1color);
        this.getModel().setPlayer1color(player1color);
    }

    /**
     * Sets the player2.
     *
     * @param player2 the new player2
     */
    public final void setPlayer2(final String player2) {
        this.getModel().setPlayer2(player2);
    }

    /**
     * Sets the player2color.
     *
     * @param player2color the new player2color
     */
    public final void setPlayer2color(final String player2color) {
        System.out.println("PLAYER2set: " + player2color);
        this.getModel().setPlayer2color(player2color);
    }

    /**
     * Sets the primary stage.
     *
     * @param newPrimaryStage the new primary stage
     */
    public final void setPrimaryStage(final Stage newPrimaryStage) {
        Gui.primaryStage = newPrimaryStage;
    }

    /**
     * Sets the root.
     *
     * @param newRoot the new root
     */
    public final void setRoot(final VBox newRoot) {
        this.root = newRoot;
    }

    /**
     * Sets the scene.
     *
     * @param newScene the new scene
     */
    public final void setScene(final Scene newScene) {
        this.scene = newScene;
    }

    /**
     * Sets the text.
     *
     * @param text the text
     * @return the button
     */
    private Button setText(final String text) {

        final int i50 = 50;

        // generate textnode
        Button button = new Button();
        button.setMinSize(i50, i50);
        button.setDisable(true);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        Text textNode = new Text(text);
        textNode.setFill(Color.BLACK);

        // relate
        button.setGraphic(textNode);

        // fix display of disabled button
        button.setOpacity(1);

        // return node
        return button;

    }

    /**
     * Sets the time.
     *
     * @param newTime the new time
     */
    public final void setTime(final Button newTime) {
        this.time = newTime;
    }

    /**
     * Sets the time counter.
     *
     * @param newTimeCounter the new time counter
     */
    public final void setTimeCounter(final int newTimeCounter) {
        this.timeCounter = newTimeCounter;
    }

    /**
     * Sets the time grid.
     *
     * @param newTimeGrid the new time grid
     */
    public final void setTimeGrid(final GridPane newTimeGrid) {
        this.timeGrid = newTimeGrid;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public final void setTitle(final String title) {
        this.model.setTitle(title);
    }

    /**
     * Win popup.
     *
     * @param popupText the popup text
     */
    public final void winPopup(final String popupText) {

        final Popup popup = new Popup();

        Button restart = new Button("Restart");
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {

                // renew
                Gui newGui = Gui.renew();
                newGui.setModel(Control.getGui().getModel());
                newGui.setTitle(Control.getGui().getTitle());
                newGui.setPlayer1(Control.getGui().getPlayer1());
                newGui.setPlayer2(Control.getGui().getPlayer2());
                newGui.setPlayer1color(Control.getGui().getPlayer1color());
                newGui.setPlayer2color(Control.getGui().getPlayer2color());

                System.out.println(newGui.getTitle());

                // render
                newGui.renderPlayfield();

                // release
                Gui.primaryStage.setScene(newGui.getScene());

                // hide popup
                popup.hide();
            }
        });

        final int i10 = 10;

        HBox popupContent = new HBox(i10);
        popupContent
                .setStyle("-fx-background-color: white; -fx-padding: 10 10;");
        popupContent.getChildren().addAll(new Text(popupText), restart);
        popup.getContent().addAll(popupContent);

        popup.show(Control.getGui().getPrimaryStage());

    }

}
