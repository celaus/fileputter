/**
 * @author  Claus Matzinger
 * @date    Jul 2, 2010
 * @file    ITransferEventListener
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
package at.clma.fileputter.events;

/**
 *
 * @author claus
 */
public interface ITransferEventListener {

    public void onTransferStarted(TransferEvent evt);

    public void onTransferFinished(TransferEvent evt);

    public void onTransferAborted(TransferEvent evt, String reason);
}
