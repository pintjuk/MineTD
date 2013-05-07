package kth.pintjukarlsson.minetd.listeners;

import kth.pintjukarlsson.minetd.TileType;

public interface MapInteractionListener {
	void onTileAdded(int x, int y, TileType t);
	void onTileRM(int x, int y);
}
