package com.baeldung.interface_vs_abstract_class;

import java.io.File;

public class VideoSender implements Sender {

	@Override
	public void send(File fileToBeSent) {
		// video sending implementation code
	}

}