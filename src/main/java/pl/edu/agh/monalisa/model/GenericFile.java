package pl.edu.agh.monalisa.model;

import java.io.File;
import java.nio.file.Path;

public abstract class GenericFile {
    private final String name;
    private final Path path;

    public GenericFile(String name, Path parentDirectoryPath) {
        this.name = name;
        if (parentDirectoryPath == null)
            path = Path.of(this.name);
        else
            this.path = parentDirectoryPath.resolve(Path.of(this.name));
    }

    public GenericFile(String name, Package<?> parentDirectory) {
        this(name, parentDirectory.getPath());
    }

    public String getName() {
        return name;
    }


    public Path getPath() {
        return path;
    }

    public void create() {
        File mainAppFile = this.path.toFile();
        mainAppFile.mkdir();
    }

    public void delete() {
        File[] files = this.path.toFile().listFiles();
        if (files != null) {
            for (final File file : files) {
                deleteRecursive(file);
            }
        }
        this.path.toFile().delete();
    }

    private void deleteRecursive(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                deleteRecursive(file);
            }
        }
        dir.delete();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
