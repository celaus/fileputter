/**
 * @author  Claus Matzinger
 * @date    Jun 13, 2010
 * @file    StationInfoFactory
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
package at.clma.fileputter.stationData;

import java.text.ParseException;
import java.util.StringTokenizer;

/**
 *
 * @author Claus Matzinger
 */
public class StationInfoFactory {

    public static IStationInfo newInstanceFromString(String s) throws ParseException {
        StringTokenizer stok = new StringTokenizer(s, "=;");

        String tmpStr = null;
        String name = null;

        int responseId = -1, // responds to which request?
                id = -1, // the package's own id
                nameCnt = 0, // how often did "name" occur?
                respCnt = 0, // ... same with "responds to"
                idCnt = 0, // .. same with "id"
                tokenCount = 0;

        Token tok = Token.ERROR;

        // check if length is correct
        if (stok.countTokens() == ((Token.values().length - 1) * 2)) {
            // iterate through tokens
            while (stok.hasMoreTokens()) {
                tmpStr = stok.nextToken();

                // current token count for error handling
                tokenCount++;

                if (tmpStr.equals(StationInfo.NAMEPREFIX) && nameCnt == 0) {
                    tok = Token.NAME;
                } else if (tmpStr.equals(StationInfo.RESPONSEPREFIX) && respCnt == 0) {
                    tok = Token.RESPONSEID;
                } else if (tmpStr.equals(StationInfo.IDPREFIX) && idCnt == 0) {
                    tok = Token.ID;
                } else {
                    throw new ParseException(s, tokenCount);
                }

                switch (tok) {
                    case NAME:
                        name = stok.nextToken();
                        nameCnt++;
                        break;
                    case RESPONSEID:
                        responseId = Integer.parseInt(stok.nextToken().trim());
                        respCnt++;
                        break;
                    case ID:
                        id = Integer.parseInt(stok.nextToken().trim());
                        idCnt++;
                        break;
                    default:
                        throw new ParseException(s, tokenCount);
                }
            }
            StationInfo st = new StationInfo(name, responseId, id);

            return st;
        } else {
            throw new ParseException(s, tokenCount);
        }
    }

    private enum Token {

        NAME, RESPONSEID, ID, ERROR
    }
}
