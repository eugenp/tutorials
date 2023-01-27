package com.baeldung.object.copy;

import java.util.HashSet;

public class DeepCloneablePlaylist extends Playlist implements Cloneable {

	@Override
	public Playlist clone() throws CloneNotSupportedException {
		Playlist cloned = (Playlist)super.clone();
		cloned.setSongs(new HashSet<>(getSongs()));
		return cloned;
	}
}
