
package com.aspose.maven.utils;
/**
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeJavaAPI {
    private String _name;
    private boolean _selected;
    private String _mavenRepositoryURL;

    public AsposeJavaAPI() {
        _selected = false;

    }

    /**
     * @return the _name
     */
    public String get_name() {
        return _name;
    }

    /**
     * @param _name the _name to set
     */
    public void set_name(String _name) {
        this._name = _name;
    }

    /**
     * @return the _selected
     */
    public boolean is_selected() {
        return _selected;
    }

    /**
     * @param _selected the _selected to set
     */
    public void set_selected(boolean _selected) {
        this._selected = _selected;
    }


    public String get_mavenRepositoryURL() {
        return _mavenRepositoryURL;
    }

    /**
     * @param _mavenRepositoryURL the _downloadFileName to set
     */
    public void set_mavenRepositoryURL(String _mavenRepositoryURL) {
        this._mavenRepositoryURL = _mavenRepositoryURL;
    }
}
