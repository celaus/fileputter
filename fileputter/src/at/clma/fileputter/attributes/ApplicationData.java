/**
 * @author  Claus Matzinger
 * @date    Jun 13, 2010
 * @file    ApplicationData
 *
 * Simple filesharing over LAN.
 * Copyright (C) 2010  Claus Matzinger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.clma.fileputter.attributes;

/**
 * Contains useful Information about author, license, name and year and
 * other application-wide names.
 * 
 * @author Claus Matzinger
 */
public class ApplicationData {

    /**
     * The applications' name.
     */
    public static final String NAME = "fileputter";
    /**
     * The current version.
     */
    public static final double VERSION = 0.1;
    /**
     * The original author of this program.
     */
    public static final String AUTHOR = "Claus Matzinger";
    /**
     * The year when development began.
     */
    public static final String CREATION_YEAR = "2010";
    /**
     * A brief description.
     */
    public static final String DESCRIPTION =
            "fileputter is a program for simple file sharing over your "
            + "local network.";
    /**
     * A very brief description.
     */
    public static final String DESCRIPTION_SHORT = "Simple filesharing over LAN";
    /**
     * The web address of the projects hosting site.
     */
    public static final String WEB = "http://code.google.com/p/fileputter";
    /**
     * Copyright string (Copyright(C) 2010 Claus Matzinger)
     */
    public static final String COPYRIGHT = "Copyright (C) " + CREATION_YEAR + " " + AUTHOR;
    /**
     * The short version of the GPL v3 license.
     */
    public static final String LICENSE =
            "This program is free software: you can redistribute it and/or modify "
            + "it under the terms of the GNU General Public License as published by "
            + "the Free Software Foundation, either version 3 of the License, or "
            + "(at your option) any later version. \n\n"
            + "This program is distributed in the hope that it will be useful, "
            + "but WITHOUT ANY WARRANTY; without even the implied warranty of "
            + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
            + "GNU General Public License for more details. "
            + "You should have received a copy of the GNU General Public License "
            + "along with this program.  If not, see <http://www.gnu.org/licenses/>. ";
    /**
     * The port, fileputter listens on.
     */
    public static final int PORT = 9999;
    // ----------
    // PREFERENCES Nodenames
    // ----------
    /**
     * Name of the "auto announcement on startup option" node for saving.
     */
    public static final String OPT_AUTOANNOUNCE = "autoAnnounce";
    /**
     * Name of the "auto reply on requests" node for saving.
     */
    public static final String OPT_AUTOREPLY = "autoReply";
    /**
     * Name of the station-node for saving.
     */
    public static final String OPT_STATIONNAME = "stationName";
    /**
     * Clear to send code.
     */
    public static final String CO_CTS = "cts";
    /**
     * End of transmission code.
     */
    public static final String CO_EOT = "eot";
    /**
     * Verbosity state of the application
     */
    private static boolean verbose = false;

    /**
     * Gets the verbosity state.
     * @return Verbosity state.
     */
    public static boolean isVerbose() {
        return verbose;
    }

    /**
     * Sets the verbosity state.
     * @param v The verbosity state.
     */
    public static void setVerbose(boolean v) {
        verbose = v;
    }
}
