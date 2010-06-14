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
 * Contains useful Information about author, license, name and year.
 * 
 * @author Claus Matzinger
 */
public class ApplicationData {

    public static final String NAME = "fileputter";
    public static final double VERSION = 0.1;
    public static final String AUTHOR = "Claus Matzinger";
    public static final String CREATION_YEAR = "2010";
    public static final String DESCRIPTION =
            "fileputter is a program for simple file sharing over your "
            + "local network.";
    public static final String DESCRIPTION_SHORT = "Simple filesharing over LAN";
    public static final String WEB = "http://code.google.com/p/fileputter";
    public static final String COPYRIGHT = "Copyright (C) " + CREATION_YEAR + " " + AUTHOR;
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
}