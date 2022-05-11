// Copyright 2002 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ServerInfo.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;


public class ServerInfo {
    public static String _serverName = "gtc-phase2";
    public static String _serverPort = "8080";
    public static String _urlPrefix = "http://";

    public static void setServerName(String serverName) {
        _serverName = serverName;
    }

    public static String getServerName() {
        return _serverName;
    }

    public static void setServerPort(int serverPort) {
        _serverPort = new Integer(serverPort).toString();
    }

    public static String getServerPort() {
        return _serverPort;
    }

    public static String getUrlPrefix() {
        return _urlPrefix;
    }

    public static void setUrlPrefix(String urlPrefix) {
        _urlPrefix = urlPrefix;
    }

    public static String getServerURL() {
        return getUrlPrefix() + getServerName() + ":" + getServerPort() + "/";
    }
}
