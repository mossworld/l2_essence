/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2jmobius.gameserver.network.serverpackets.surveillance;

import java.util.LinkedList;
import java.util.List;

import org.l2jmobius.gameserver.data.sql.CharInfoTable;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.network.serverpackets.ServerPacket;

/**
 * @author MacuK
 */
public class ExUserWatcherTargetList extends ServerPacket
{
	private final List<TargetInfo> _info = new LinkedList<>();
	
	public ExUserWatcherTargetList(Player player)
	{
		for (int objId : player.getSurveillanceList())
		{
			final String name = CharInfoTable.getInstance().getNameById(objId);
			final Player target = World.getInstance().getPlayer(objId);
			boolean online = false;
			int level = 0;
			int classId = 0;
			if (target != null)
			{
				online = true;
				level = target.getLevel();
				classId = target.getClassId().getId();
			}
			else
			{
				level = CharInfoTable.getInstance().getLevelById(objId);
				classId = CharInfoTable.getInstance().getClassIdById(objId);
			}
			_info.add(new TargetInfo(name, online, level, classId));
		}
	}
	
	@Override
	public void write()
	{
		ServerPackets.EX_USER_WATCHER_TARGET_LIST.writeId(this);
		writeInt(_info.size());
		for (TargetInfo info : _info)
		{
			writeSizedString(info._name);
			writeInt(0); // client.getProxyServerId()
			writeInt(info._level);
			writeInt(info._classId);
			writeByte(info._online ? 1 : 0);
		}
	}
	
	private static class TargetInfo
	{
		private final String _name;
		private final int _level;
		private final int _classId;
		private final boolean _online;
		
		public TargetInfo(String name, boolean online, int level, int classId)
		{
			_name = name;
			_online = online;
			_level = level;
			_classId = classId;
		}
	}
}