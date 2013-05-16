package kth.pintjukarlsson.minetd.interfaces;

import com.badlogic.gdx.InputProcessor;

import kth.pintjukarlsson.minetd.listeners.MapInteractionListener;

public interface MouseInputAdapterService {
	/**
	 * 
	 * @param mapInteractionListener
	 */
	void setMapInteractionListener(MapInteractionListener mapInteractionListener);
	/**
	 * 
	 */
	void init();
	/**
	 * 
	 * @return
	 */
	InputProcessor getProcessor();

}
