package ru.bellintegrator.practice.handbook.view;

public class DocView {

    public String name;

    public Integer code;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + code;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        DocView docView = (DocView) obj;
        if (code != docView.code)
            return false;

        if (!name.equals(docView.name))
            return false;

        return true;
    }
}
