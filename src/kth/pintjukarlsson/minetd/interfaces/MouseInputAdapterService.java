package kth.pintjukarlsson.minetd.interfaces;

import kth.pintjukarlsson.minetd.listeners.MapInteractionListener;

public interface MouseInputAdapterService {
	/**
	 * 
	 * @param mapInteractionListener
	 */
	void setMapInteractionListener(MapInteractionListener mapInteractionListener);

	void init();

}
