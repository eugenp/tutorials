package com.baeldung.object.copy;

public class ShallowCloneablePlaylist extends Playlist implements Cloneable {

@Override
public Playlist clone() throws CloneNotSupportedException {
	return (Playlist)super.clone();
}
}
