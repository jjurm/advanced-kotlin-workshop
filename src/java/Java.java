package java;

import org.jetbrains.annotations.NotNull;

interface HasName {
    @NotNull
    String getName();
}

class Language implements HasName {

    private String name;

    public Language(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    public void hello() {
        System.out.println("Written in language " + getName() + "!");
    }
}
