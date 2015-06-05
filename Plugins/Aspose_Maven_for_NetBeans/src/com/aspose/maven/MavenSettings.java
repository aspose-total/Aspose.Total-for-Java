package com.aspose.maven;

import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

public final class MavenSettings {

    private static final String PROP_LAST_ARCHETYPE_GROUPID = "lastArchetypeGroupId"; 
    private static final String PROP_LAST_ARCHETYPE_VERSION = "lastArchetypeVersion"; 

    private Preferences getPreferences() {
        return NbPreferences.forModule(MavenSettings.class);
    }

    public String getLastArchetypeGroupId() {
        return getPreferences().get(PROP_LAST_ARCHETYPE_GROUPID,"com.mycompany");
    }

    public String getLastArchetypeVersion() {
        return getPreferences().get(PROP_LAST_ARCHETYPE_VERSION, "1.0-SNAPSHOT"); 
    }

    public void setLastArchetypeVersion(String version) {
        putProperty(PROP_LAST_ARCHETYPE_VERSION, version); 
    }

    public void setLastArchetypeGroupId(String groupId) {
        putProperty(PROP_LAST_ARCHETYPE_GROUPID, groupId);
    }

    private String putProperty(String key, String value) {
        String retval = getProperty(key);
        if (value != null) {
            getPreferences().put(key, value);
        } else {
            getPreferences().remove(key);
        }
        return retval;
    }
    private static final MavenSettings INSTANCE = new MavenSettings();

    public static MavenSettings getDefault() {
        return INSTANCE;
    }

    private String getProperty(String key) {
        return getPreferences().get(key, null);
    }

}
