package fr.univ_amu.m1info.board_game_library.graphics.javafx.bar;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;


import java.util.HashMap;
import java.util.Map;

public class Bar extends HBox {
    private final Map<String, Labeled> labeledElements = new HashMap<>();
    private final Map<String, Button> buttons = new HashMap<>();

    public Bar() {
        super();
        setSpacing(10);
        setPrefHeight(30);
        setAlignment(Pos.CENTER);
    }

    public synchronized void addLabel(String id, String initialText){
        if(labeledElements.containsKey(id)){
            return;
        }
        Label label = new Label(initialText);
        label.setAlignment(Pos.BASELINE_CENTER);
        labeledElements.put(id, label);
        this.getChildren().add(label);
    }

    public synchronized void removeLabeledElement(String id){
        if(labeledElements.containsKey(id)){
            Labeled labeled = labeledElements.get(id);
            getChildren().remove(labeled);
            labeledElements.remove(id);
            buttons.remove(id);
        }
    }

    public void setButtonAction(String id, ButtonActionOnClick buttonActionOnClick){
        if(!buttons.containsKey(id)){
            throw new IllegalArgumentException("Button " + id + " does not exist");
        }
        buttons.get(id).setOnAction(e -> buttonActionOnClick.onClick());
    }

    public synchronized void addButton(String id, String label){
        Button button = new Button(label);
        labeledElements.put(id, button);
        buttons.put(id, button);
        this.getChildren().add(button);
    }

    public void updateLabel(String id, String newText){
        if(labeledElements.containsKey(id)){
            labeledElements.get(id).setText(newText);
        }
    }
}
