package de.dailab.plistacontest.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Serializer {

	private static Logger logger = LoggerFactory.getLogger(Serializer.class);
	
	public static void serialize(final Object _obj, final String name) {
		try {
			final FileOutputStream fileOut = new FileOutputStream(name);
			final ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(_obj);
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
}
