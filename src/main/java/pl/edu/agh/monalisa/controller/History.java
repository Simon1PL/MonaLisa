package pl.edu.agh.monalisa.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TreeItem;
import pl.edu.agh.monalisa.model.AssignmentFile;
import pl.edu.agh.monalisa.model.GenericFile;

import java.util.LinkedList;

public class History {
    private final LinkedList<AssignmentFile> itemStack = new LinkedList<>();
    private final LinkedList<AssignmentFile> undoneStack = new LinkedList<>();
    private final BooleanProperty isUndoDisabled = new SimpleBooleanProperty(true);
    private final BooleanProperty isRedoDisabled = new SimpleBooleanProperty(true);
    private AssignmentFile currentItem;


    public GenericFile undo() {
        if (itemStack.isEmpty()) return null;
        var item = itemStack.pop();
        undoneStack.push(currentItem);
        currentItem = item;

        updateAvailability();
        return item;
    }

    public GenericFile redo() {
        if (undoneStack.isEmpty()) return null;
        var item = undoneStack.pop();
        itemStack.push(currentItem);
        currentItem = item;

        updateAvailability();
        return item;
    }

    public BooleanProperty isRedoDisabled() {
        return isRedoDisabled;
    }

    public BooleanProperty isUndoDisabled() {
        return isUndoDisabled;
    }

    public void add(AssignmentFile chosenFile) {
        // don't add to history after undo/redo:
        if (currentItem != null && currentItem == chosenFile) return;

        if (currentItem != null) {
            itemStack.push(currentItem);
            undoneStack.clear();
            isUndoDisabled.set(false);
            isRedoDisabled.set(true);
        }
        currentItem = chosenFile;
    }

    private void updateAvailability() {
        isRedoDisabled.set(undoneStack.isEmpty());
        isUndoDisabled.set(itemStack.isEmpty());
    }
}
