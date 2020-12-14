package pl.edu.agh.monalisa.model;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import pl.edu.agh.monalisa.constants.AvailableExtensionsEnum;
import pl.edu.agh.monalisa.constants.AvailableExtensionsEnum.AvailableExtensions;
import pl.edu.agh.monalisa.loader.FileContentEvent;
import pl.edu.agh.monalisa.loader.FileSystemEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AssignmentFile extends GenericFile {
    private AvailableExtensions extension;
    private Notes notes = new Notes();
    private String text;
    private Path notesPath;
    private Disposable fileContentListener;
    private StringProperty content;

    public AssignmentFile(String name, Path parent) {
        super(name, parent);
        this.extension = AvailableExtensionsEnum.getExtension(name);
        this.loadTextFromFile();
        this.notesPath = this.getPath().getParent().resolve(this.getName().replace(".", "") + "notes.json");
        this.content = new SimpleStringProperty();
        try {
            if (this.notesPath.toFile().exists()) {
                this.notes = new Gson().fromJson(Files.readString(this.notesPath), Notes.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

    public void loadTextFromFile() {
        try {
            this.text = this.getPath().toFile().exists() ? Files.readString(this.getPath()) : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<? extends Package> getChildren() {
        return null;
    }

    public void addNote(Note note) {
        this.notes.addNote(note);
        this.saveNotes();
    }

    public void removeNote(Note note) {
        this.notes.removeNote(note);
        this.saveNotes();
    }

    public void editNote(Note note) {
        this.notes.editNote(note);
        this.saveNotes();
    }

    public void saveNotes() {
        try {
            if (!this.notesPath.toFile().exists()) {
                this.notesPath.toFile().createNewFile();
            }
            FileWriter fileWriter = new FileWriter(this.notesPath.toFile());
            fileWriter.write(new Gson().toJson(this.notes));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Disposable getFileContentListener() {
        return fileContentListener;
    }

    public void setFileContentListener(Disposable fileContentListener) {
        this.fileContentListener = fileContentListener;
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}
