/**
 * Copyright (c) Aspose 2002-2014. All Rights Reserved.
 *
 * LICENSE: This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not,
 * see http://opensource.org/licenses/gpl-3.0.html
 *
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 *
 */
package com.aspose.utils;

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
